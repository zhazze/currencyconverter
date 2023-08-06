package com.fdmgroup.currencyconverterproject.transaction;

public class UserNotFoundException extends Exception {
	
	private String message;

	public UserNotFoundException(String message) {
		this.message = message;
	}

}
