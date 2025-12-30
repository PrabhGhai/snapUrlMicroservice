package com.SnapUrl.url_service.exceptions;


public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) {
        super(message);
    }
}

