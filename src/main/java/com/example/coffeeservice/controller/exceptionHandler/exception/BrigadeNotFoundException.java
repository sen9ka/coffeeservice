package com.example.coffeeservice.controller.exceptionHandler.exception;

public class BrigadeNotFoundException extends RuntimeException {
    public BrigadeNotFoundException(String message) {
        super(message);
    }
}
