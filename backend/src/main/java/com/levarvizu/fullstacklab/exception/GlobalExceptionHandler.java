package com.levarvizu.fullstacklab.exception;

import com.levarvizu.fullstacklab.dto.ErrorResponse;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.levarvizu.fullstacklab.dto.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ProductNotFoundException.class
    )

    public ResponseEntity<ErrorResponse>
            handleProductNotFound(
                    ProductNotFoundException ex
            ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                ex.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ValidationErrorResponse>
            handleValidationError(
                    MethodArgumentNotValidException ex
            ) {

        Map<String, String> errors
                = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error
                        -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        return ResponseEntity
                .badRequest()
                .body(
                        new ValidationErrorResponse(
                                errors
                        )
                );
    }
}
