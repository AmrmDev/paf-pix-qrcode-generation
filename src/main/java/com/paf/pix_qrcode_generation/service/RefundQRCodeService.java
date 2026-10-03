package com.paf.pix_qrcode_generation.service;

import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.repository.PixRepository;
import jakarta.validation.constraints.NotBlank;
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

        Pix pix = pixRepository
                .findByTxid(txid)
                .orElseThrow(() -> new RuntimeException("Pix not found"));

        log.info("Setting Pix Status to REFUNDED");
        pix.setStatus(PixStatus.REFUNDED);

        log.info("Done! Pix Status set to REFUNDED");

        return pixRepository.save(pix);
    }
}