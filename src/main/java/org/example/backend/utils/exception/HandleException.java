package org.example.backend.utils.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
