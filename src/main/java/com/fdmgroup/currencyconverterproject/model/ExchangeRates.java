package com.fdmgroup.currencyconverterproject.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/*
 * Represents a collection of currency exchange rates.
 */
public class ExchangeRates {
	
	private Map<String, Currency> currencies;
	
	/**
     * Constructs a new ExchangeRates object with an empty map of currencies.
     */
	public ExchangeRates() {
		this.currencies = new HashMap<>();
	}

	/**
     * Adds the currency information in the exchange rates.
     *
     * @param currencyCode The currency code (e.g., usd, eur).
     * @param currency     The Currency object containing currency information.
     */
	@JsonAnySetter
    public void setCurrency(String currencyCode, Currency currency) {
        currencies.put(currencyCode, currency);
    }
	
	/**
     * Gets the map of currencies with their currency codes as keys.
     *
     * @return The map of currencies.
     */
	public Map<String, Currency> getCurrencies() {
		return currencies;
	}
	
	/**
    * Retrieves the Currency object for the specified currency code.
    *
    * @param currencyCode The currency code (e.g., usd, eur).
    * @return The Currency object containing currency information.
    */
	public Currency getCurrencyInfo(String currencyCode) {
		return currencies.get(currencyCode);
	}
	
	/**
     * Calculates the exchange rate between two currencies.
     *
     * @param fromCurrencyCode The currency code to convert from.
     * @param toCurrencyCode   The currency code to convert to.
     * @return The exchange rate between the two currencies.
     *         If the from and to currencies are the same, returns 1.0.
     *         If either currency is not found, returns 0.0.
     */
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
