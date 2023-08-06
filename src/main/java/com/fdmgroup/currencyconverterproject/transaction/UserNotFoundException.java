package com.fdmgroup.currencyconverterproject.transaction;

/**
 * Exception class for handling cases where a user profile is not found.
 */
public class UserNotFoundException extends Exception {
	
	private String message;

	public UserNotFoundException(String message) {
		this.message = message;
	}

}
