package com.paf.pix_qrcode_generation.controller;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class QrCodeGenerationController {

    @PostMapping("/generate")
    public ResponseEntity<?> generateQrCode(@Valid @RequestBody PixRequestDTO requestDTO) {
        log.info(
                "Generating Pix QR Code - requestId={}, amount={}, channel={}",
                requestDTO.requestId(),
                requestDTO.amount(),
                requestDTO.channel()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
