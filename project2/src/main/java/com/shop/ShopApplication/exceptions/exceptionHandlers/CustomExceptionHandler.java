package com.shop.ShopApplication.exceptions.exceptionHandlers;

import com.shop.ShopApplication.exceptions.VerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<Object> handleVerificationException(VerificationException ex) {
        String errorMessage = "User verification failed: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
}
