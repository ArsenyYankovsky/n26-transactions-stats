package com.em.n26transactionsstats.exception;

import com.em.n26transactionsstats.util.ErrorType;

public class BusnessException extends Exception {
	private ErrorType errorType;

	public ErrorType getErrorType() {
		return errorType;
	}

	public BusnessException(ErrorType errorType) {
		// passing the error type code and the message to default constructor
		super(errorType.toString());
		//Assigning the error to local variable
		this.errorType = errorType;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2143511215782026294L;

}
