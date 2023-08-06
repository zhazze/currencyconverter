package com.fdmgroup.currencyconverterproject.converter;

import com.fdmgroup.currencyconverterproject.model.ExchangeRates;
/*
 * A utility class that performs currency conversion based on provided exchange rates.
 */
public class Converter {
	
	ExchangeRates exchangeRates;
	
	public Converter(ExchangeRates exchangeRates) {
		this.exchangeRates = exchangeRates;
	}
	
	/**
     * Converts the specified amount from one currency to another using the exchange rates.
     *
     * @param fromCurrency The currency code of the source currency.
     * @param toCurrency   The currency code of the target currency.
     * @param amount       The amount to be converted.
     * @return The converted amount in the TO currency.
     * @throws IllegalArgumentException If the exchange rate between the specified currencies is not available.
     */
	public double convert(String fromCurrency, String toCurrency, double amount) {
		
		double exchangeRate = exchangeRates.getExchangeRate(fromCurrency, toCurrency);
		if (exchangeRate == 0.0) {
	        throw new IllegalArgumentException("Invalid exchange rate between " + fromCurrency + " and " + toCurrency);
	    }
		
		double convertedAmount = amount * exchangeRate;
		
		return convertedAmount;
	}

}
