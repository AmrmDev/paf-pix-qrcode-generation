package com.paf.pix_qrcode_generation.service;

import com.paf.pix_qrcode_generation.entity.Pix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QRCodeService {

    public String generate(Pix pix) {
        log.info("QRCodeService.generate method started!");

        return "QR_CODE_GENERATED";
    }
}