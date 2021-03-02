package com.igwines.controller;

import com.igwines.exceptions.CodeNotValidException;
import com.igwines.exceptions.NotValidUserDetailsException;
import com.igwines.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map> userNotFoundExceptionHandler(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("code", String.valueOf(HttpStatus.NOT_FOUND.value()));
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotValidUserDetailsException.class, CodeNotValidException.class})
    public ResponseEntity<Map> notValidUserDetailsExceptionHandler(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
