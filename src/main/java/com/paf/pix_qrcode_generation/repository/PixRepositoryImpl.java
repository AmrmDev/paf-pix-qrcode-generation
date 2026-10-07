package com.paf.pix_qrcode_generation.repository;

import com.paf.pix_qrcode_generation.entity.Pix;
import com.paf.pix_qrcode_generation.entity.PixStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PixRepositoryImpl implements PixRepository {

    private static final String TABLE = "pix-qrcodes";

    private final DynamoDbClient dynamoDbClient;

    @Override
    public Pix save(Pix pix) {

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("requestId", AttributeValue.builder().s(pix.getRequestId()).build());
        item.put("txid", AttributeValue.builder().s(pix.getTxid().toString()).build());
        item.put("amount", AttributeValue.builder().s(pix.getAmount()).build());
        item.put("pixKey", AttributeValue.builder().s(pix.getPixKey()).build());
        item.put("status", AttributeValue.builder().s(pix.getStatus().name()).build());
        item.put("expiresAt", AttributeValue.builder().s(pix.getExpiresAt().toString()).build());

        log.info("PERSISTING DATA INTO DYNAMODB");
        PutItemRequest request = PutItemRequest.builder().tableName(TABLE).item(item).build();

        logged("putItem", () -> dynamoDbClient.putItem(request));

        log.info("DATA SAVED INTO DYNAMODB");
        return pix;
    }

    @Override
    public Optional<Pix> findByTxid(UUID txid) {

        Map<String, AttributeValue> key = Map.of(
                "txid", AttributeValue.builder().s(txid.toString()).build());

        GetItemRequest request = GetItemRequest.builder().tableName(TABLE).key(key).build();

        Map<String, AttributeValue> item =
                logged("getItem", () -> dynamoDbClient.getItem(request)).item();

        if (item.isEmpty()) {
            return Optional.empty();
        }

        Pix pix = new Pix();
        pix.setRequestId(item.get("requestId").s());
        pix.setTxid(UUID.fromString(item.get("txid").s()));
        pix.setAmount(item.get("amount").s());
        pix.setPixKey(item.get("pixKey").s());
        pix.setStatus(PixStatus.valueOf(item.get("status").s()));
        pix.setExpiresAt(Instant.parse(item.get("expiresAt").s()));

        return Optional.of(pix);
    }

    /** Loga o tempo da chamada (DEBUG) e, se falhar, o codigo de erro da AWS. */
    private <T> T logged(String operation, Supplier<T> call) {
        long start = System.nanoTime();
        try {
            T result = call.get();
            log.debug("DynamoDB {} ok in {} ms", operation, (System.nanoTime() - start) / 1_000_000);
            return result;
        } catch (DynamoDbException e) {
            String code = e.awsErrorDetails() != null ? e.awsErrorDetails().errorCode() : "unknown";
            log.error("DynamoDB {} failed after {} ms: table={} awsErrorCode={} statusCode={}",
                    operation, (System.nanoTime() - start) / 1_000_000, TABLE, code, e.statusCode(), e);
            throw e;
        } catch (RuntimeException e) {
            log.error("DynamoDB {} failed after {} ms: table={}",
                    operation, (System.nanoTime() - start) / 1_000_000, TABLE, e);
            throw e;
        }
    }

    private void logged(String operation, Runnable call) {
        logged(operation, () -> {
            call.run();
            return null;
        });
    }
}