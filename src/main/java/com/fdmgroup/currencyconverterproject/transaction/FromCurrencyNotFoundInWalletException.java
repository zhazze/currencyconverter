package com.fdmgroup.currencyconverterproject.transaction;

/**
 * Exception class for handling cases where the 'from' currency is not found in the user's wallet.
 */
public class FromCurrencyNotFoundInWalletException extends Exception {
	
	private String message;

	public FromCurrencyNotFoundInWalletException(String message) {
		this.message = message;
	}

}
