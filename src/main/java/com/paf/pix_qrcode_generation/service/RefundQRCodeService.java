package com.paf.pix_qrcode_generation.service;

import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import com.paf.pix_qrcode_generation.repository.PixRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundQRCodeService {

    private final PixRepository pixRepository;

    public Pix refund(@NotBlank String s, UUID txid) {

        Pix pix = pixRepository
                .findByTxid(txid)
                .orElseThrow(() -> new RuntimeException("Pix not found"));

        pix.setStatus(PixStatus.REFUNDED);

        return pixRepository.save(pix);
    }
}