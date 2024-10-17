package com.example.coffeeservice.controller.exceptionHandler;

import com.example.coffeeservice.controller.exceptionHandler.exception.BrigadeNotFoundException;
import com.example.coffeeservice.controller.exceptionHandler.exception.GrainArrivalMessageProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GrainExceptionHandler {
    @ExceptionHandler(GrainArrivalMessageProcessingException.class)
    public ResponseEntity<Object> handleGrainArrivalMessageProcessingException(GrainArrivalMessageProcessingException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrigadeNotFoundException.class)
    public ResponseEntity<Object> handleBrigadeNotFoundExceptionException(BrigadeNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
