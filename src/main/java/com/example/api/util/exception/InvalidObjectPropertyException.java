package com.example.api.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidObjectPropertyException extends RuntimeException {

    private static final long serialVersionUID = 1L;
	
	public InvalidObjectPropertyException(String ex) {
		super(ex);
	}
    
}
