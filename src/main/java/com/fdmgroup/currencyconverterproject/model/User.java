package com.fdmgroup.currencyconverterproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	private String name;
	private Wallet wallet;
	
	@JsonCreator
	public User(
			@JsonProperty("name") String name,
			@JsonProperty("wallet") Wallet wallet) {
		super();
		this.name = name;
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	
}