package com.touchtunes.jukeboxesapp.handler;

import com.touchtunes.jukeboxesapp.exception.BadRequestException;
import com.touchtunes.jukeboxesapp.exception.ResourceNotFoundException;
import com.touchtunes.jukeboxesapp.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleValidationException(BadRequestException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleValidationException(ResourceNotFoundException ex) {
        Response response = new Response(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
