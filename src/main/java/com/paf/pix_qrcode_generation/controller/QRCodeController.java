package com.paf.pix_qrcode_generation.controller;

import com.paf.pix_qrcode_generation.dto.output.*;
import com.paf.pix_qrcode_generation.dto.input.*;
import com.paf.pix_qrcode_generation.usecase.GenerateQRCodeUseCase;
import com.paf.pix_qrcode_generation.usecase.PayQRCodeUseCase;
import com.paf.pix_qrcode_generation.usecase.RefundQRCodeUseCase;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class QRCodeController {

    private final GenerateQRCodeUseCase generateQRCodeUseCase;
    private final RefundQRCodeUseCase refundQRCodeUseCase;
    private final PayQRCodeUseCase payQRCodeUseCase;

    public QRCodeController(GenerateQRCodeUseCase generateQRCodeUseCase, RefundQRCodeUseCase refundQRCodeUseCase, PayQRCodeUseCase payQRCodeUseCase) {
        this.generateQRCodeUseCase = generateQRCodeUseCase;
        this.refundQRCodeUseCase = refundQRCodeUseCase;
        this.payQRCodeUseCase = payQRCodeUseCase;
    }

    @PostMapping("/generate")
    public ResponseEntity<QRCodeResponseDTO> generate(@Valid @RequestBody PixRequestDTO request) {
        log.info("Initializing generateQRCodeUseCase.execute method");
        QRCodeResponseDTO response = generateQRCodeUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/payQRcode")
    public ResponseEntity<QRCodePaymentResponseDTO> payQRCode(@Valid @RequestBody QRCodePaymentRequestDTO request) {
        log.info("Payment request received! Calling payQRCodeUseCase.execute method.");
        QRCodePaymentResponseDTO response = payQRCodeUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/refundQRCode")
    public ResponseEntity<QRCodeRefundResponseDTO> refund(@Valid @RequestBody QRCodeRefundRequestDTO request) {
        log.info("Initializing refundQRCodeUseCase.execute method");
        QRCodeRefundResponseDTO response = refundQRCodeUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
