package com.example.api.exceptions;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Response not found");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
    
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Conflict");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationFailedExcetion(AuthenticationFailedException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(UploadFileException.class)
    protected ResponseEntity<ErrorResponse> handleUploadFileException(UploadFileException ex) {
        log.error("handleUploadFileException throw UploadFileException : {}", ex.getErrorCode());
        return ErrorResponse.toResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(value = {AWSException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleAwsException(AWSException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal Error");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

}
