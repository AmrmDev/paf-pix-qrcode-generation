package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;
import com.paf.pix_qrcode_generation.entity.Pix;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GenerateQRCodeUseCaseImpl implements GenerateQRCodeUseCase {

    @Override
    public QRCodeResponseDTO execute(PixRequestDTO request) {

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
        pix.setChannel(request.channel());


        return null;
    }
}