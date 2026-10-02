package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.input.QRCodePaymentRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodePaymentResponseDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeRefundResponseDTO;

public interface PayQRCodeUseCase {
    QRCodeRefundResponseDTO execute(QRCodePaymentResponseDTO request);
}
