package com.example.coffeeservice.controller.exceptionHandler.exception;

public class GrainArrivalMessageProcessingException extends RuntimeException {
    public GrainArrivalMessageProcessingException(String message) {
        super(message);
    }
}
