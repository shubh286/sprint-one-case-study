package com.appexception;

import com.annotations.ExcludedFromGeneratedCodeCoverage;

@ExcludedFromGeneratedCodeCoverage
public class OrderNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrderNotFoundException(){
		
	}
	public String toString() {
		return "Order Not Found";
	}

}