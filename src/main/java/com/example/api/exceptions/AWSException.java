package com.example.api.exceptions;

public class AWSException extends RuntimeException {
    
    public AWSException(String message){
        super(message);
    }

}
