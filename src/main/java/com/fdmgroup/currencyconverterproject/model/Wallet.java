package com.fdmgroup.currencyconverterproject.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/* Adding of new currencies to wallet
 * And updated wallet get serialized back to json format
 */

public class Wallet {
	
	private Map<String, Double> wallet = new HashMap<>();
	
	@JsonAnyGetter
    public Map<String, Double> getWallet() {
        return wallet;
    }

    @JsonAnySetter
    public void setWallet(String currency, Double amount) {
        wallet.put(currency, amount);
    }
    
    public Double getBalance(String currency) {
    	return wallet.getOrDefault(currency, 0.0);
    }
    
    public void addCurrency(String currency, Double amount) {
    	wallet.putIfAbsent(currency, 0.0);
    	wallet.put(currency, wallet.get(currency) + amount);
    }
    
    public boolean containsCurrency(String currency) {
    	return wallet.containsKey(currency);
    }
    
    public boolean containsCurrencyAndAmount(String currency, Double amount) {
    	return wallet.containsKey(currency) && wallet.get(currency) >= amount;
    }

}
