package com.traccapp.demo.exception;

public class FileUploadException extends RuntimeException{
    public FileUploadException(String message){
        super(message);
    }

    public FileUploadException(String message, Exception exception){
        super(message,exception);
    }
}
