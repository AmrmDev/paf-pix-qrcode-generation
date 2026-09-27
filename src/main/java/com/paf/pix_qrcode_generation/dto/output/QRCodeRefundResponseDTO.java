package com.paf.pix_qrcode_generation.dto.output;

public record QRCodeRefundResponseDTO(
        String requestId,
        String txid,
        String status,
        String message
) {
}