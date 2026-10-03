package com.paf.pix_qrcode_generation.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record QRCodeRefundRequestDTO(
        @NotBlank String requestId,
        @NotNull UUID txid
) {}