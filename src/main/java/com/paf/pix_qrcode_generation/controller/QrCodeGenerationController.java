package com.paf.pix_qrcode_generation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QrCodeGenerationController {

    @PostMapping("/generate")
    public ResponseEntity<?> generateQrCode(@RequestBody PixRequestDTO request) {


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
