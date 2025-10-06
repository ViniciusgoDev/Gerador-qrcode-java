package com.viniciusgomes.qrcode.controller;

import com.viniciusgomes.qrcode.dto.QrCodeGenerateRequest;
import com.viniciusgomes.qrcode.dto.QrCodeGenerateResponse;
import com.viniciusgomes.qrcode.service.QrCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeContoller {

    private final QrCodeService qrCodeService;


    public QrCodeContoller(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;


    }

    @PostMapping
    public QrCodeGenerateResponse gerarQrCode(@RequestBody QrCodeGenerateRequest texto) throws Exception {
        return qrCodeService.gerarQrCode(texto.texto());
    }
}
