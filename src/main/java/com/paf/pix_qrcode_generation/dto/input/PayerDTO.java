package com.paf.pix_qrcode_generation.dto.input;

public record PayerDTO(
        String cpf,
        String name,
        String bank,
        String accountType,
        String accountNumber,
        String agency
){}
