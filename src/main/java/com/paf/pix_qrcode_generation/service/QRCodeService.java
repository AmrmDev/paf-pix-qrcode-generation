package com.paf.pix_qrcode_generation.service;

import com.paf.pix_qrcode_generation.entity.Pix;
import org.springframework.stereotype.Service;

@Service
public class QRCodeService {

    public String generate(Pix pix) {

        // futuramente teremos a lógica real de geração
        return "QR_CODE_GENERATED";
    }
}