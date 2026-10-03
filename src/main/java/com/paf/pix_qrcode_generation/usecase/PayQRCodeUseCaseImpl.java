package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.QRCodePaymentRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodePaymentResponseDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeRefundResponseDTO;
import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Slf4j
@RequiredArgsConstructor
@Service
public class PayQRCodeUseCaseImpl implements PayQRCodeUseCase {

    private final PixRepository pixRepository;

    @Override
    public QRCodePaymentResponseDTO execute(QRCodePaymentRequestDTO request) {

        log.info("payQRCodeUseCase.execute method started!");

        Pix pix = pixRepository.findByTxid(request.txid())
                .orElseThrow(() -> new RuntimeException(
                        "Pix not found for txid: " + request.txid()
                ));

        if (pix.getStatus() != PixStatus.PENDING) {
            throw new IllegalStateException(
                    "Pix cannot be paid. Current status: " + pix.getStatus()
            );
        }

        if (Instant.now().isAfter(pix.getExpiresAt())) {
            pix.setStatus(PixStatus.EXPIRED);
            pixRepository.save(pix);

            throw new IllegalStateException("Pix has expired");
        }

        pix.setStatus(PixStatus.PAID);

        pixRepository.save(pix);

        log.info("Pix {} successfully paid", pix.getTxid());

        return new QRCodePaymentResponseDTO(
                pix.getRequestId(),
                pix.getTxid().toString(),
                pix.getStatus().toString(),
                "Payment processed successfully"
        );
    }
}