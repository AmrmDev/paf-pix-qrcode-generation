package com.paf.pix_qrcode_generation.usecase;


import com.paf.pix_qrcode_generation.dto.input.QRCodeRefundRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeRefundResponseDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;
import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.service.RefundQRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefundQRCodeUseCaseImpl implements RefundQRCodeUseCase {

    private final RefundQRCodeService refundQRCodeService;

    @Override
    public QRCodeRefundResponseDTO execute(QRCodeRefundRequestDTO request) {

        log.info("refundQRCodeUseCase.execute method started!");

        Pix pix = refundQRCodeService.refund(
                request.txid()
        );

        return new QRCodeRefundResponseDTO(
                pix.getRequestId(),
                pix.getTxid(),
                pix.getStatus().name(),
                "QR Code refunded successfully"
        );

    }
}
