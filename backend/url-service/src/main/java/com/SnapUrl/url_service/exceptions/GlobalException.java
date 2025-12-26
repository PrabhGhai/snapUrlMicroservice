package com.SnapUrl.url_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<?> handleUrlNotFound(UrlNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UrlInactiveException.class)
    public ResponseEntity<?> handleUrlInactive(UrlInactiveException ex) {
        return ResponseEntity
                .status(HttpStatus.GONE) // 410
                .body(ex.getMessage());
    }

    @ExceptionHandler(UrlAlreadyExistException.class)
    public ResponseEntity<?> handleUrlAlreadyExists(UrlAlreadyExistException  ex)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

}

