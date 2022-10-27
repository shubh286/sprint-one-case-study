package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.appexception.AdminExistsException;
import com.appexception.AdminNotFoundException;
import com.appexception.AdminNotLoggedException;
import com.appexception.EmptyCartException;
import com.appexception.EmptyListReturnedException;
import com.appexception.FeedbackNotFoundException;
import com.appexception.ListEmptyException;
import com.appexception.NoCardFoundException;
import com.appexception.NullValuesFoundException;
import com.appexception.ObjectAddFailException;
import com.appexception.OrderNotFoundException;
import com.appexception.PaymentNotFoundException;
import com.appexception.ProductNotFoundException;
import com.appexception.UserNotFoundException;
import com.appexception.UserNotLoginException;

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
    @ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException i,WebRequest req)
    {
        
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListEmptyException.class)
	public ResponseEntity<Object> handleNullValuesFoundException(ListEmptyException i,WebRequest req)
    {
        
        return  new ResponseEntity<>(i.toString(),HttpStatus.FOUND);
    }
    @ExceptionHandler(NullValuesFoundException.class)
	public ResponseEntity<Object> handleNullValuesFoundException(NullValuesFoundException i,WebRequest req)
    {
        
        return  new ResponseEntity<>(i.toString(),HttpStatus.FOUND);
    }
    @ExceptionHandler(UserNotLoginException.class)
    public ResponseEntity<Object> handleUserNotLoginException(UserNotLoginException i,WebRequest req)
    {
    	return new ResponseEntity<Object>(i.toString(),HttpStatus.FOUND);
    }
    
    
    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Object> handleAdminNotFoundException(AdminNotFoundException i,WebRequest req)
    {

        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AdminExistsException.class)
    public ResponseEntity<Object> handleAdminExistsException(AdminExistsException i,WebRequest req)
    {

        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AdminNotLoggedException.class)
    public ResponseEntity<Object> handleAdminNotLoggedException(AdminNotLoggedException i,WebRequest req)
    {

        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(EmptyListReturnedException.class)
    public ResponseEntity<Object> handleEmptyListException(EmptyListReturnedException i,WebRequest req)
    {

        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ObjectAddFailException.class)
    public ResponseEntity<Object> handleAddNotDoneException(ObjectAddFailException i,WebRequest req)
    {

        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException i,WebRequest req)
    {
        
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException i,WebRequest req)
    {
        
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FeedbackNotFoundException.class)
    public ResponseEntity<Object> handlePaymentNotFoundException(FeedbackNotFoundException i,WebRequest req){
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Object> handleEmptyCartException(EmptyCartException i,WebRequest req){
        return  new ResponseEntity<>(i.toString(),HttpStatus.NOT_FOUND);
    }
}