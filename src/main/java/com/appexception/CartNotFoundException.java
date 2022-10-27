package com.appexception;

public class CartNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartNotFoundException()
	{
		
	}
	public String toString()
	{
		return "Cart Not Found";
	}
}
