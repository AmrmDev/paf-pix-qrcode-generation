package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;

public interface GenerateQRCodeUseCase {

    QRCodeResponseDTO execute(PixRequestDTO request, String requestId);
}