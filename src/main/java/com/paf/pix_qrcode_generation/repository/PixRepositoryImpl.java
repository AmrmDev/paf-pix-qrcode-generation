package com.paf.pix_qrcode_generation.repository;

import com.paf.pix_qrcode_generation.entity.Pix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PixRepositoryImpl implements PixRepository {

    @Override
    public Optional<Pix> findByRequestIdAndTxid(String requestId, UUID txid) {
        return Optional.empty();
    }

    @Override
    public Pix save(Pix pix) {
        return pix;
    }
}