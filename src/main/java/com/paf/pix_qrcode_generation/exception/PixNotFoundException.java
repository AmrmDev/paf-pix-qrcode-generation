package com.paf.pix_qrcode_generation.exception;

import java.util.UUID;

public class PixNotFoundException extends RuntimeException {
    public PixNotFoundException(UUID txid) {
        super("Pix not found for txid: " + txid);
    }
}