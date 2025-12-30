package com.SnapUrl.url_service.exceptions;

public class NoSuchUrlFound extends RuntimeException {

    public NoSuchUrlFound(String msg)
    {
         super(msg);
    }
}
