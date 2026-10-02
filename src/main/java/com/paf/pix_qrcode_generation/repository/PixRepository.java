package com.paf.pix_qrcode_generation.repository;


import com.paf.pix_qrcode_generation.entity.Pix;

import java.util.Optional;
import java.util.UUID;

public interface PixRepository {

    Optional<Pix> findByRequestIdAndTxid(String requestId, UUID txid);

    Optional<Pix> findByTxid(UUID txid);

    Pix save(Pix pix);
}