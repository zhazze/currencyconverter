package com.fdmgroup.currencyconverterproject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.currencyconverterproject.model.ExchangeRates;
import com.fdmgroup.currencyconverterproject.model.User;
import com.fdmgroup.currencyconverterproject.transaction.InsufficientFromAmountException;
import com.fdmgroup.currencyconverterproject.transaction.TransactionProcessor;
import com.fdmgroup.currencyconverterproject.transaction.UserNotFoundException;
import com.fdmgroup.currencyconverterproject.converter.Converter;


public class Runner {
	
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
		}
		
		File updatedUsersFile = new File("updatedUsers.json");
		transactionProcessor.updateUsersFile(updatedUsersFile, users);
	}

}
