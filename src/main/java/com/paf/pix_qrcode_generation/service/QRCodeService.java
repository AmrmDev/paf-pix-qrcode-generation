package com.paf.pix_qrcode_generation.service;

import com.paf.pix_qrcode_generation.entity.Pix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.github.thgrcarvalho.pix.PixPayload;

import java.math.BigDecimal;

@Slf4j
@Service
public class QRCodeService {

    public String generate(Pix pix) {
        log.info("QRCodeService.generate method started!");

        String txId = pix.getTxid()
                .toString()
                .replace("-", "")
                .substring(0, 25);

        PixPayload pixPayload = PixPayload.newBuilder()
                .pixKey(pix.getPixKey())
                .merchantName(pix.getReceiverName())
                .merchantCity(pix.getReceiverCity())
                .amount(new BigDecimal(pix.getAmount()))
                .txId(txId)
                .dynamic(false)
                .build();

        String encoded = pixPayload.encode();

        log.info("Pix Copia e Cola generated: {}", encoded);

        return encoded;

    }
}