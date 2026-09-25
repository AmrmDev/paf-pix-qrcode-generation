package com.paf.pix_qrcode_generation.dto.input;

public record ReceiverDTO(
        String name,
        String bank,
        String agency,
        String accountNumber,
        String city
) {}
