package com.viniciusgomes.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.viniciusgomes.qrcode.dto.QrCodeGenerateResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeService {

    private final S3Service s3Service;

    public QrCodeService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public QrCodeGenerateResponse gerarQrCode(String texto) throws WriterException, IOException {
        byte[] qrBytes = gerarQRCodeEmBytes(texto);

        String fileName = "qrcode_" + java.util.UUID.randomUUID() + ".png";
        String url = s3Service.upload(qrBytes, fileName);

        return new QrCodeGenerateResponse(url);
    }

    private byte[] gerarQRCodeEmBytes(String texto) throws WriterException, IOException {
        if (texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("O texto para gerar QR Code não pode ser vazio.");
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }
}
