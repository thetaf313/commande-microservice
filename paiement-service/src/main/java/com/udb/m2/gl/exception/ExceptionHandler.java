package com.udb.m2.gl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ErrorDTO handleException(Exception exception){
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()+"")
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CompteServiceNotFoundException.class})
    public ErrorDTO handleException(CompteServiceNotFoundException exception){
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.value()+"")
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {CompteServiceException.class})
    public ErrorDTO handleException(CompteServiceException exception){
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value()+"")
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationsException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });
        return errors;
    }

}
