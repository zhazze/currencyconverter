package com.fdmgroup.currencyconverterproject.transaction;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.currencyconverterproject.converter.Converter;
import com.fdmgroup.currencyconverterproject.model.User;
import com.fdmgroup.currencyconverterproject.model.ExchangeRates;


public class TransactionProcessor {
	
	private Converter converter;
	private List<User> users;
	private ExchangeRates exchangeRates;
	private static final String logFile = "transactionLog.txt";
	private static final Logger logger = LogManager.getLogger(TransactionProcessor.class);
	 
	public TransactionProcessor(Converter converter, List<User> users, ExchangeRates exchangeRates) {
		this.converter = converter;
		this.users = users;
		this.exchangeRates = exchangeRates;
	}

	public void executeTransaction(String transactionFile) throws IOException, UserNotFoundException, InsufficientFromAmountException {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] transaction = line.split(" ");
				
				System.out.println("Processing transaction: " + line);
				
				String userName = transaction[0];
				String fromCurrency = transaction[1];
				String toCurrency = transaction[2];
				double amount = Double.parseDouble(transaction[3]);
				
				User user = validateUser(userName);
				if (user == null) {
					logger.warn("Transaction skipped for user: " + userName + ". User profile not found.");
					throw new UserNotFoundException("User profile does not exist in User file.");
				}
				
				if (!user.getWallet().containsCurrencyAndAmount(fromCurrency, amount)) {
					logger.warn("Transaction skipped for user: " + userName + ". Insufficient funds for " + fromCurrency + ".");
					throw new InsufficientFromAmountException("User has insufficient balance in the FROM currency.");
				}
				
				double exchangeRate = exchangeRates.getExchangeRate(fromCurrency, toCurrency);
				
				if (exchangeRate == 0.0) {
					logger.error("Transaction failed for user: " + userName + ". The FROM currency " + fromCurrency + " or TO currency " + toCurrency + "could not be found.");
					continue;	
				}
				
				if (fromCurrency.equalsIgnoreCase(toCurrency)) {
					logger.warn("Transaction skipped for user: " + userName + ". Converting the same exchange rates from: " + fromCurrency + " to " + toCurrency + ".");
					continue;	
				}
				System.out.println("Before conversion: " + user.getWallet().getBalance(fromCurrency) + " " + fromCurrency);
				double convertedAmount = converter.convert(fromCurrency, toCurrency, amount);
				System.out.println("Converted amount: " + convertedAmount + " " + toCurrency);
				System.out.println("After conversion: " + user.getWallet().getBalance(fromCurrency) + " " + fromCurrency);
				user.getWallet().setWallet(fromCurrency, user.getWallet().getBalance(fromCurrency) - amount);
				user.getWallet().setWallet(toCurrency, user.getWallet().getBalance(toCurrency) + convertedAmount);
				logger.info("Transaction for user: " + userName + " was successful.");
				System.out.println("Updated wallet: " + user.getWallet());
	
			}
						
		} catch (FileNotFoundException e) {
			logger.error("Transaction file could not be found." + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error while reading the transaction file." + e.getMessage());
			e.printStackTrace();
		} finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
		
	}
	
	private User validateUser(String userName) {
		for (User user : users) {
			if (user.getName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	public void updateUsersFile(File updatedUsersFile, List<User> users) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter();

		try {
		objectMapper.writeValue(updatedUsersFile, users);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
