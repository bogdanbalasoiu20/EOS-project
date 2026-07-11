package com.example.demo.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(TaskNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                                                                LocalDateTime.now(),
                                                                HttpStatus.NOT_FOUND.value(),
                                                                ex.getMessage(),
                                                                null));
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(TaskAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                                                            LocalDateTime.now(),
                                                            HttpStatus.CONFLICT.value(),
                                                            ex.getMessage(),
                                                            null));
    }

    @ExceptionHandler(TaskAlreadyCompletedException.class)
    public ResponseEntity<ErrorResponse> handle(TaskAlreadyCompletedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                                                            LocalDateTime.now(),
                                                            HttpStatus.CONFLICT.value(),
                                                            ex.getMessage(),
                                                            null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ErrorResponse(
                                                    LocalDateTime.now(),
                                                    HttpStatus.BAD_REQUEST.value(),
                                                    "Validation failed",
                                                    errors));
    }

    @ExceptionHandler(StatusTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(StatusTypeNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(StatusTypeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(StatusTypeAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage(),
                        null
                ));
    }
}
