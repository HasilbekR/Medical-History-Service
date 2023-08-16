package com.example.medicalhistoryservice.config;

import com.example.medicalhistoryservice.exception.AuthenticationFailedException;
import com.example.medicalhistoryservice.exception.DataNotFoundException;
import com.example.medicalhistoryservice.exception.RequestValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFound(DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(value = {AuthenticationFailedException.class})
    public ResponseEntity<String> accessDenied(AuthenticationFailedException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }

    @ExceptionHandler(value = {RequestValidationException.class})
    public ResponseEntity<String> requestValidationException(RequestValidationException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

}
