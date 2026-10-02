package com.paf.pix_qrcode_generation.dto.input;

import jakarta.validation.constraints.NotBlank;

public record QRCodePaymentRequestDTO(
        @NotBlank String requestId,
        @NotBlank String txid
) {}