package com.times.tmdb.exceptionHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.Null;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler  {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest webRequest){
        String errorMessageDescription=exception.getLocalizedMessage();
        if(errorMessageDescription==null)
            errorMessageDescription=exception.toString();
        ErrorMessage errorMessage=new ErrorMessage(new Date(),errorMessageDescription);
        return new ResponseEntity<>(
                errorMessage,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception, WebRequest webRequest){
        String errorMessageDescription=exception.getLocalizedMessage();
        if(errorMessageDescription==null)
            errorMessageDescription=exception.toString();
        ErrorMessage errorMessage=new ErrorMessage(new Date(),errorMessageDescription);
        return new ResponseEntity<>(
                errorMessage,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}