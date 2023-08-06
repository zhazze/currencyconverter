package com.fdmgroup.currencyconverterproject.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;


public class ExchangeRates {
	
	private Map<String, Currency> currencies;
	
	public ExchangeRates() {
		this.currencies = new HashMap<>();
	}

	@JsonAnySetter
    public void setCurrency(String currencyCode, Currency currency) {
        currencies.put(currencyCode, currency);
    }
	
	public Map<String, Currency> getCurrencies() {
		return currencies;
	}
	
	// main method of retrieving Exchange Rate Currency Code's info
	public Currency getCurrencyInfo(String currencyCode) {
		return currencies.get(currencyCode);
	}
	
	public double getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
		if (fromCurrencyCode.equalsIgnoreCase(toCurrencyCode)) {
			return 1.0;
		}
		
		Currency fromCurrency = currencies.get(fromCurrencyCode);
		Currency toCurrency = currencies.get(toCurrencyCode);
		
		if (fromCurrency == null || toCurrency == null) {
			return 0.0;
		}
		
		return toCurrency.getRate() / fromCurrency.getRate();
	}

}
