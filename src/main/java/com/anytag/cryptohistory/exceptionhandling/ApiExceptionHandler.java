package com.anytag.cryptohistory.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;
import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AnyTagException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(AnyTagException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorMessage> dateTimeParseException(DateTimeParseException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }
}
