package com.cabs.allocation.demo.exception;

public class RequestFailedValidationException extends Exception {

    String errorMessage;

    RequestFailedValidationException(String msg) {
        this.errorMessage = msg;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
