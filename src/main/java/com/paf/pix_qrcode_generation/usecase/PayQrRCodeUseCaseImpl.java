package com.paf.pix_qrcode_generation.usecase;

import com.paf.pix_qrcode_generation.dto.output.QRCodePaymentResponseDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeRefundResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class PayQrRCodeUseCaseImpl implements PayQRCodeUseCase {
    @Override
    public QRCodeRefundResponseDTO execute(QRCodePaymentResponseDTO request) {

        return null;
    }
}
