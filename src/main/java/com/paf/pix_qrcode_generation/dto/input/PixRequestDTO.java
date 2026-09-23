package com.paf.pix_qrcode_generation.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixRequestDTO(
        @NotBlank String requestId,
        @NotBlank String amount,
        @NotBlank String pixKey,
        @NotNull Long expiration,
        @NotNull PayerDTO payer,
        @NotNull ReceiverDTO receiver,
        String description,
        @NotBlank String channel
) {}