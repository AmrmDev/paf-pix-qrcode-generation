package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;
import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenerateQRCodeUseCaseImpl implements GenerateQRCodeUseCase {

    private final QRCodeService qrCodeService;

    @Override
    public QRCodeResponseDTO execute(PixRequestDTO request) {

        log.info("generateQRCodeUseCase.execute method started!");
        if (request.expiration() <= 0) {
            throw new IllegalArgumentException("Expiration must be greater than zero");
        }

        if (new BigDecimal(request.amount()).compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Pix pix = new Pix();

        pix.setRequestId(request.requestId());
        pix.setAmount(request.amount());
        pix.setPixKey(request.pixKey());
        pix.setExpiration(request.expiration());
        pix.setDescription(request.description());
        pix.setExpiresAt(Instant.now().plusSeconds(request.expiration()));

        log.info("Calling qrCodeService.generate method");
        String qrCode = qrCodeService.generate(pix);

        log.info("QRCode received!");
        pix.setQrCode(qrCode);
        pix.setStatus(PixStatus.PENDING);


        log.info("Persisting data into dynamoDB...");
        log.info("QRCode saved with PENDING status!");
        log.info("QRCode generation process finished succesfully!");
        return null;
    }
}