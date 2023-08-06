package com.fdmgroup.currencyconverterproject.converter;

import com.fdmgroup.currencyconverterproject.model.ExchangeRates;

public class Converter {
	
	ExchangeRates exchangeRates;
	
	public Converter(ExchangeRates exchangeRates) {
		this.exchangeRates = exchangeRates;
	}
	
	public double convert(String fromCurrency, String toCurrency, double amount) {
		
		double exchangeRate = exchangeRates.getExchangeRate(fromCurrency, toCurrency);
		if (exchangeRate == 0.0) {
	        throw new IllegalArgumentException("Invalid exchange rate between " + fromCurrency + " and " + toCurrency);
	    }
		
		double convertedAmount = amount * exchangeRate;
		
		return convertedAmount;
	}

}
