package com.cabs.allocation.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleEntityNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestFailedValidationException.class)
    protected ResponseEntity<ErrorMessage> handleValidationFailed(RequestFailedValidationException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }
}
