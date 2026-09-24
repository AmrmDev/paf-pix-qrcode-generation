package com.paf.pix_qrcode_generation.controller;

import com.paf.pix_qrcode_generation.dto.input.PixRequestDTO;
import com.paf.pix_qrcode_generation.dto.output.QRCodeResponseDTO;
import com.paf.pix_qrcode_generation.usecase.GenerateQRCodeUseCase;
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
public class QRCodeController {

    private final GenerateQRCodeUseCase generateQRCodeUseCase;

    public QRCodeController(GenerateQRCodeUseCase generateQRCodeUseCase) {
        this.generateQRCodeUseCase = generateQRCodeUseCase;
    }

    @PostMapping("/generate")
    public ResponseEntity<QRCodeResponseDTO> generate(@Valid @RequestBody PixRequestDTO request) {
        QRCodeResponseDTO response = generateQRCodeUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
