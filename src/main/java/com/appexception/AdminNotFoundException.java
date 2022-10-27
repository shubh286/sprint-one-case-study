package com.appexception;

import com.annotations.Generated;

@Generated
public class AdminNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminNotFoundException()
	{
		
	}
	public String toSring()
	{
		return "Admin Not Found";
	}
}
