package com.paf.pix_qrcode_generation.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Pix {

    private UUID txid;

    private String requestId;
    private String amount;
    private String pixKey;
    private String qrCode;
    private Long expiration;
    private String description;

    private PixStatus status;

    private Instant createdAt;
    private Instant expiresAt;

    public Pix() {
        this.id = UUID.randomUUID();
    }
}