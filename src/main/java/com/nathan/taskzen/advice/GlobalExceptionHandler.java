package com.nathan.taskzen.advice;


import com.nathan.taskzen.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/*@ControllerAdvice is a specialized component in Spring that:

Handles exceptions thrown by controllers (global exception handler)

Works like a filter/interceptor for the @Controller layer

Can also handle things like data binding, validation errors, etc.

It's like a Global Try-Catch Block for your entire REST API layer. Centralized, clean, and beautifully reusable.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationError(MethodArgumentNotValidException exception){

        //Here Hash mapping is used to map the errors with their respected fields names, since both are strings
        // we are mapping string to string
        Map<String, String> fieldErrors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->{
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String,Object> response = new HashMap<>();

        response.put("Time Stamp: ", LocalDateTime.now());
        response.put("Http Status", HttpStatus.BAD_REQUEST.value());
        response.put("ERRORS ", fieldErrors);


    return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", "Internal Server Error: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
