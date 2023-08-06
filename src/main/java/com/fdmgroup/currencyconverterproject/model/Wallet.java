package com.fdmgroup.currencyconverterproject.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Represents a wallet that holds different currencies and their amounts.
 * Allows adding new currencies and updating the wallet in JSON format.
 */
public class Wallet {
	
	private Map<String, Double> wallet = new HashMap<>();
	
	/**
     * Gets the entire wallet containing currencies and their respective amounts.
     *
     * @return The map of currencies and their amounts in the wallet.
     */
	@JsonAnyGetter
    public Map<String, Double> getWallet() {
        return wallet;
    }

	/**
     * Sets the amount of a currency in the wallet.
     *
     * @param currency The currency to set the amount for.
     * @param amount   The amount to set for the currency.
     */
    @JsonAnySetter
    public void setWallet(String currency, Double amount) {
        wallet.put(currency, amount);
    }
    
    /**
     * Gets the balance of a specific currency in the wallet.
     *
     * @param currency The currency to check the balance for.
     * @return The balance of the specified currency in the wallet.
     */
    public Double getBalance(String currency) {
    	return wallet.getOrDefault(currency, 0.0);
    }
    
    /**
     * Adds a new currency to the wallet with the specified amount.
     *
     * @param currency The currency to add.
     * @param amount   The amount to set for the new currency.
     */
    public void addCurrency(String currency, Double amount) {
    	wallet.putIfAbsent(currency, 0.0);
    	wallet.put(currency, wallet.get(currency) + amount);
    }
    
    /**
     * Checks if the wallet contains a specific currency.
     *
     * @param currency The currency to check.
     * @return {true} if the currency is present in the wallet, otherwise {false}.
     */
    public boolean containsCurrency(String currency) {
    	return wallet.containsKey(currency);
    }
    
    /**
     * Checks if the wallet contains a specific currency with a sufficient amount.
     *
     * @param currency The currency to check.
     * @param amount   The amount to check for sufficiency.
     * @return {true} if the currency is present with sufficient amount, otherwise {false}.
     */
    public boolean containsCurrencyAndAmount(String currency, Double amount) {
    	return wallet.containsKey(currency) && wallet.get(currency) >= amount;
    }

}
