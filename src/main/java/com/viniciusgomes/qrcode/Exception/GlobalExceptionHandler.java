package com.viniciusgomes.qrcode.Exception;

import com.viniciusgomes.qrcode.Exception.Exceptions.QrCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("erro", "Erro interno no servidor, por favor tente novamente mais tarde.");
        body.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(QrCodeException.class)
    public ResponseEntity<Object> handleQrCodeException(QrCodeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("erro", "Erro ao gerar QR Code");
        body.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


}

