package com.cpnv.bijavaaws.controller;

import com.cpnv.bijavaaws.exceptions.ObjectAlreadyExistsException;
import com.cpnv.bijavaaws.exceptions.ObjectNotFoundException;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class FallbackController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handleException(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<String> handleException(ObjectAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MimeTypeException.class)
    public ResponseEntity<String> handleException(MimeTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
