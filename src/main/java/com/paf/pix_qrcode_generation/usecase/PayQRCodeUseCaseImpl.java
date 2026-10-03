package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.QRCodePaymentRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodePaymentResponseDTO;
import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.exception.InvalidPixStateException;
import com.paf.pix_qrcode_generation.exception.PixNotFoundException;
import com.paf.pix_qrcode_generation.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class PayQRCodeUseCaseImpl implements PayQRCodeUseCase {

    private final PixRepository pixRepository;

    @Override
    public QRCodePaymentResponseDTO execute(QRCodePaymentRequestDTO request) {

        try (var ignoredTx = MDC.putCloseable("txid", request.txid().toString());
             var ignoredReq = MDC.putCloseable("pixRequestId", request.requestId())) {

            log.info("Processing payment");

            Pix pix = pixRepository.findByTxid(request.txid())
                    .orElseThrow(() -> new PixNotFoundException(request.txid()));

            if (pix.getStatus() != PixStatus.PENDING) {
                throw new InvalidPixStateException(
                        "Pix cannot be paid. Current status: " + pix.getStatus());
            }

            if (Instant.now().isAfter(pix.getExpiresAt())) {
                pix.setStatus(PixStatus.EXPIRED);
                pixRepository.save(pix);
                log.info("Pix expired before payment: PENDING -> EXPIRED (expiresAt={})", pix.getExpiresAt());
                throw new InvalidPixStateException("Pix has expired");
            }

            pix.setStatus(PixStatus.PAID);
            pixRepository.save(pix);

            log.info("Pix paid: PENDING -> PAID");

            return new QRCodePaymentResponseDTO(
                    pix.getRequestId(),
                    pix.getTxid().toString(),
                    pix.getStatus().toString(),
                    "Payment processed successfully"
            );
        }
    }
}