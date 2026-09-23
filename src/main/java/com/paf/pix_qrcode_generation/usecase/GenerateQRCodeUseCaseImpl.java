package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class GenerateQRCodeUseCaseImpl implements GenerateQRCodeUseCase {

    @Override
    public QRCodeResponseDTO execute(PixRequestDTO request) {


        return null;
    }
}