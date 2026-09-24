package com.paf.pix_qrcode_generation.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Pix {

    private UUID id;

    private String requestId;
    private String amount;
    private String pixKey;
    private Long expiration;
    private String description;
    private String channel;

    public Pix() {
        this.id = UUID.randomUUID();
    }
}