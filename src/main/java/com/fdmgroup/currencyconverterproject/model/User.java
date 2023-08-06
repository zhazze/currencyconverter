package com.fdmgroup.currencyconverterproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user with a name and a wallet.
 * The user information follows the structure of its JSON file
 * User info is being deserialized from JSON to Java objects
 */
public class User {
	
	private String name;
	private Wallet wallet;
	
	/**
     * Constructs a new User object with the specified name and wallet.
     *
     * @param name   The name of the user.
     * @param wallet The wallet of the user.
     */
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