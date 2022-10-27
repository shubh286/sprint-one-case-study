package com.appexception;

public class EmptyCartException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EmptyCartException()
	{
		
	}
	public String toString()
	{
		return "Cart Not Found";
	}
}
