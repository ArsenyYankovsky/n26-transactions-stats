package com.em.n26transactionsstats.util;

public enum ErrorType {

	INVALID_REPORT_SIZE("E00001",
			"Current inmemory implimentation does not support for this report size , Max allowed :"
					+ Integer.MAX_VALUE), OLD_TX("E00002","transaction is older than 60 seconds");

	ErrorType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;

	public String toString() {
		return "[Code :" + this.code + " , Message : " + this.message + "]";
	}
}
