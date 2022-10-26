package com.appexception;

public class PaymentNotFoundException extends Exception{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PaymentNotFoundException() {
        
    }
    public String toString() {
        return "Could not Set Up Payment.";
    }



}