package com.appexception;

import com.annotations.ExcludedFromGeneratedCodeCoverage;

@ExcludedFromGeneratedCodeCoverage
public class AdminNotLoggedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminNotLoggedException()
	{
		
	}
	public String toString()
	{
		return "Admin has not logged in.";
	}
}
