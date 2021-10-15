package de.neuefische.devquiz.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ControllerAdvicer extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementExeption(NoSuchElementException ex){
        log.error("Input not proceccable", ex);
        System.out.println("Catched");

        ApiError apiError= new ApiError("Ressource not found",ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
