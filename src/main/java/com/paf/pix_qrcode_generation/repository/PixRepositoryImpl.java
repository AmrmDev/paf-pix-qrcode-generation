package com.paf.pix_qrcode_generation.repository;

import com.paf.pix_qrcode_generation.entity.Pix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.paf.pix_qrcode_generation.config.DynamoDBConfig;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PixRepositoryImpl implements PixRepository {

    private final DynamoDbClient dynamoDbClient;

    @Override
    public Optional<Pix> findByRequestIdAndTxid(String requestId, UUID txid) {
        return Optional.empty();
    }

    @Override
    public Pix save(Pix pix) {
        return pix;
    }
}