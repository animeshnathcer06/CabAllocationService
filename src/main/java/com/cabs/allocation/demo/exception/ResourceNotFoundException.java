package com.cabs.allocation.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

    private String errorMessage;

    public ResourceNotFoundException(String msg) {
        errorMessage = msg;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
