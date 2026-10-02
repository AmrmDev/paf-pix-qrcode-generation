package com.paf.pix_qrcode_generation.repository;

import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PixRepositoryImpl implements PixRepository {

    private final DynamoDbClient dynamoDbClient;

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
                .s(pix.getAmount().toString())
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

    public Optional<Pix> findByTxid(UUID txid) {

        Map<String, AttributeValue> key = new HashMap<>();

        key.put("txid", AttributeValue.builder()
                .s(txid.toString())
                .build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName("pix-qrcodes")
                .key(key)
                .build();

        Map<String, AttributeValue> item =
                dynamoDbClient.getItem(request).item();

        if (item.isEmpty()) {
            return Optional.empty();
        }

        Pix pix = new Pix();

        pix.setRequestId(item.get("requestId").s());
        pix.setTxid(UUID.fromString(item.get("txid").s()));
        pix.setAmount(item.get("amount").s());
        pix.setPixKey(item.get("pixKey").s());
        pix.setStatus(PixStatus.valueOf(item.get("status").s()));

        return Optional.of(pix);
    }
}