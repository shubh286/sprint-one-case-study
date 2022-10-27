package com.appexception;

import com.annotations.Generated;

@Generated
public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException()
	{
		
	}
	public String toString()
	{
		return "User Not Found";
	}
}
