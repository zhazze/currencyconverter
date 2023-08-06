package com.fdmgroup.currencyconverterproject.transaction;

public class InsufficientFromAmountException extends Exception {
	
	private String message;

	public InsufficientFromAmountException(String message) {
		this.message = message;
	}
}
