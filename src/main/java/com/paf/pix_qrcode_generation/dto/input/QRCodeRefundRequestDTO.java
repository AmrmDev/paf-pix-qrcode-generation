package com.paf.pix_qrcode_generation.dto.input;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record QRCodeRefundRequestDTO(

        @NotBlank
        String requestId,

        @NotBlank
        UUID txid
) {
}