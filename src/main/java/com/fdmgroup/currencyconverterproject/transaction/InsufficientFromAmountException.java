package com.fdmgroup.currencyconverterproject.transaction;

/**
 * Exception class for handling cases where a user's wallet has insufficient funds in the FROM currency.
 */
public class InsufficientFromAmountException extends Exception {
	
	private String message;

	public InsufficientFromAmountException(String message) {
		this.message = message;
	}
}
