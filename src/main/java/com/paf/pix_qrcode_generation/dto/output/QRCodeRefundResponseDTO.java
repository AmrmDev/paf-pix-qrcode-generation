package com.paf.pix_qrcode_generation.dto.output;

public record QRCodeRefundResponseDTO(
        String requestId,
        java.util.UUID txid,
        String status,
        String message
) {
}