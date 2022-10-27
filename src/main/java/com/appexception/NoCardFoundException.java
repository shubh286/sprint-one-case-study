package com.appexception;

import com.annotations.ExcludedFromGeneratedCodeCoverage;

@ExcludedFromGeneratedCodeCoverage
public class NoCardFoundException extends Exception{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public NoCardFoundException() {
        
    }
    public String toString() {
        return "No Saved Card Found in Card List.";
    }
}
