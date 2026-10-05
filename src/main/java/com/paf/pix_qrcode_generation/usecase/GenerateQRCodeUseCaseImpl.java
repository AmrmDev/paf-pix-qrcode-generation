package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;
import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.repository.PixRepository;
import com.paf.pix_qrcode_generation.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenerateQRCodeUseCaseImpl implements GenerateQRCodeUseCase {

    private final QRCodeService qrCodeService;
    private final PixRepository pixRepository;

    @Override
    public QRCodeResponseDTO execute(PixRequestDTO request) {

        Pix pix = new Pix();

        try (var ignoredTx = MDC.putCloseable("txid", pix.getTxid().toString())) {

            log.info("Generating QRCode: amount={} channel={} expirationSeconds={}",
                    request.amount(), request.channel(), request.expiration());

            if (request.expiration() <= 0) {
                throw new IllegalArgumentException("Expiration must be greater than zero");
            }

            BigDecimal amount;
            try {
                amount = new BigDecimal(request.amount());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Amount is not a valid number");
            }
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }

            pix.setAmount(request.amount());
            pix.setPixKey(request.pixKey());
            pix.setExpiration(request.expiration());
            pix.setDescription(request.description());
            pix.setExpiresAt(Instant.now().plusSeconds(request.expiration()));
            pix.setReceiverName(request.receiver().name());
            pix.setReceiverCity(request.receiver().city());

            pix.setQrCode(qrCodeService.generate(pix));
            pix.setStatus(PixStatus.PENDING);

            pixRepository.save(pix);

            log.info("QRCode generated and saved: status={} expiresAt={}", pix.getStatus(), pix.getExpiresAt());

            return new QRCodeResponseDTO(
                    pix.getRequestId(),
                    pix.getTxid().toString(),
                    pix.getStatus().name(),
                    pix.getExpiresAt()
            );
        }
    }
}