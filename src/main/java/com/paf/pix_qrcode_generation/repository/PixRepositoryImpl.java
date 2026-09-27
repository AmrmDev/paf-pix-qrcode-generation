package com.paf.pix_qrcode_generation.repository;

import com.paf.pix_qrcode_generation.entity.Pix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.paf.pix_qrcode_generation.config.DynamoDBConfig;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
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

        Map<String, AttributeValue> item = new HashMap<>();

        item.put("requestId", AttributeValue.builder()
                .s(pix.getRequestId())
                .build());

        item.put("txid", AttributeValue.builder()
                .s(pix.getTxid().toString())
                .build());

        item.put("amount", AttributeValue.builder()
                .s(pix.getAmount())
                .build());

        item.put("pixKey", AttributeValue.builder()
                .s(pix.getPixKey())
                .build());

        item.put("status", AttributeValue.builder()
                .s(pix.getStatus().name())
                .build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("pix-qrcodes")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);

        return pix;
    }
}