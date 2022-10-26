package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.appexception.NoCardFoundException;
import com.appexception.PaymentNotFoundException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
   
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Object> handlePaymentNotFoundException(PaymentNotFoundException i,WebRequest req){
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoCardFoundException.class)
    public ResponseEntity<Object> handleCartListIsEmptyException(NoCardFoundException i,WebRequest req){
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
}