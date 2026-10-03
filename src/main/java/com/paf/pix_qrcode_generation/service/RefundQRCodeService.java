package com.paf.pix_qrcode_generation.service;

import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.exception.InvalidPixStateException;
import com.paf.pix_qrcode_generation.exception.PixNotFoundException;
import com.paf.pix_qrcode_generation.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundQRCodeService {

    private final PixRepository pixRepository;

    public Pix refund(UUID txid) {

        Pix pix = pixRepository.findByTxid(txid)
                .orElseThrow(() -> new PixNotFoundException(txid));

        if (pix.getStatus() != PixStatus.PAID) {
            throw new InvalidPixStateException(
                    "Pix cannot be refunded. Current status: " + pix.getStatus());
        }

        pix.setStatus(PixStatus.REFUNDED);
        Pix saved = pixRepository.save(pix);

        log.info("Pix refunded: PAID -> REFUNDED");

        return saved;
    }
}