package com.fdmgroup.currencyconverterproject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.currencyconverterproject.model.ExchangeRates;
import com.fdmgroup.currencyconverterproject.model.User;
import com.fdmgroup.currencyconverterproject.transaction.FromCurrencyNotFoundInWalletException;
import com.fdmgroup.currencyconverterproject.transaction.InsufficientFromAmountException;
import com.fdmgroup.currencyconverterproject.transaction.TransactionProcessor;
import com.fdmgroup.currencyconverterproject.transaction.UserNotFoundException;
import com.fdmgroup.currencyconverterproject.converter.Converter;

/**
 * The Runner class is responsible for executing the currency conversion transactions and updating user information.
 */
public class Runner {
	
	/**
	 * Reads exchange rates from a JSON file.
	 *
	 * @param fx_file The JSON file containing exchange rates.
	 * @return An ExchangeRates object containing the exchange rates data.
	 */
	public static ExchangeRates readExchangeRatesFromJSONFile(File fx_file) {
		ObjectMapper objectMapper = new ObjectMapper();
		ExchangeRates exchangeRates = null;
		
		try {
			exchangeRates = objectMapper.readValue(fx_file, new TypeReference<ExchangeRates>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return exchangeRates;
	}
	
	/**
	 * Reads user information from a JSON file.
	 *
	 * @param users_file The JSON file containing user information.
	 * @return A list of User objects representing the users.
	 */
	public static List<User> readUsersFromJSONFile(File users_file) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<User> users = null;
		
		try {
			users = (List<User>) objectMapper.readValue(users_file, new TypeReference<List<User>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	/**
	 * Main method to execute currency conversion transactions and update user information.
	 */
	public static void main(String[] args) {
		
		File fx_ratesFile = new File("fx_rates.json");
		ExchangeRates exchangeRates = readExchangeRatesFromJSONFile(fx_ratesFile);
		
		File users_file = new File("users.json");
		List<User> users = readUsersFromJSONFile(users_file);
		
		Converter converter = new Converter(exchangeRates);
		
		TransactionProcessor transactionProcessor = new TransactionProcessor(converter, users, exchangeRates);
		
		String transactionFile = "transactions.txt";
		try {
			transactionProcessor.executeTransaction(transactionFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (InsufficientFromAmountException e) {
			e.printStackTrace();
		} catch (FromCurrencyNotFoundInWalletException e) {
			e.printStackTrace();
		}
		
		File updatedUsersFile = new File("updatedUsers.json");
		transactionProcessor.updateUsersFile(updatedUsersFile, users);
	}

}
