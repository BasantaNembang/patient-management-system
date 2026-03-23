package com.security.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handelValidationException(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach((error -> errors.put(error.getField(), error.getDefaultMessage())));
        return ResponseEntity.badRequest().body(errors);

    }


    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> userAlreadyExistException(
            EmailAlreadyExistException exception) {
        Map<String, String> errors = new HashMap<>();
        log.error("user already present");
        errors.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(errors);

    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> userNotFoundException(
            UserNotFoundException exception) {
        Map<String, String> errors = new HashMap<>();
        log.error("user not present in Database");
        errors.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(errors);

    }


}
