package com.dev.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldvalue){
        super(String.format("%s not found with given input data %s : %s",resourceName,fieldName,fieldvalue));
    }
}
