package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.QRCodeRefundRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;

public interface RefundQRCodeUseCase {
    QRCodeResponseDTO execute(QRCodeRefundRequestDTO request);
}
