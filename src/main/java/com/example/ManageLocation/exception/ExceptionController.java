package com.example.ManageLocation.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<Object> handleException(CustomException e){
//        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
//    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleException(CustomException e, HttpServletRequest request){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now(ZoneOffset.UTC));
        errorResponse.put("status", e.getStatus());
        errorResponse.put("message", e.getMessage());
        errorResponse.put("path", request.getRequestURI());
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors); // HTTP 400 with error messages
    }
}
