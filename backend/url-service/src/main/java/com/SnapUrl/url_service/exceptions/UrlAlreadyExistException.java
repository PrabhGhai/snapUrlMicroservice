package com.SnapUrl.url_service.exceptions;

public class UrlAlreadyExistException extends RuntimeException{
   public UrlAlreadyExistException(String msg)
   {
       super(msg);
   }
}
