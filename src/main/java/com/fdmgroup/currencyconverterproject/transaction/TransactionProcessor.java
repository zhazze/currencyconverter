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

/**
 * Handles processing of transactions using exchange rates and user wallets.
 */
public class TransactionProcessor {
	
	private Converter converter;
	private List<User> users;
	private ExchangeRates exchangeRates;
	private static final String logFile = "transactionLog.txt";
	private static final Logger logger = LogManager.getLogger(TransactionProcessor.class);
	 
	/**
     * Constructs a new TransactionProcessor with the provided Converter, user list, and exchange rates.
     *
     * @param converter     The converter to use for currency conversions.
     * @param users         The list of users with their wallets.
     * @param exchangeRates The exchange rates for different currencies.
     */
	public TransactionProcessor(Converter converter, List<User> users, ExchangeRates exchangeRates) {
		this.converter = converter;
		this.users = users;
		this.exchangeRates = exchangeRates;
	}

	/**
     * Executes transactions read from a transaction file.
     *
     * @param transactionFile         The path of the transaction file to process.
     * @throws IOException            If there is an error reading the transaction file.
     * @throws UserNotFoundException  If a user is not found in the user list.
     * @throws InsufficientFromAmountException If the user's wallet has insufficient funds or does not contain currency in the FROM currency.
     * @throws FromCurrencyNotFoundInWalletException If the user's wallet does not contain currency in the FROM currency.
     */
	public void executeTransaction(String transactionFile) throws IOException, UserNotFoundException, InsufficientFromAmountException, FromCurrencyNotFoundInWalletException {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] transaction = line.split(" ");
								
				String userName = transaction[0];
				String fromCurrency = transaction[1];
				String toCurrency = transaction[2];
				double amount = Double.parseDouble(transaction[3]);
				
				User user = validateUser(userName);
				if (user == null) {
					try { throw new UserNotFoundException("User profile does not exist in User file.");
					} catch (UserNotFoundException e) {
						logger.warn("Transaction skipped for user: " + userName + ". User profile not found.");
					} continue;
				}
				
				if (!user.getWallet().containsCurrency(fromCurrency)) {
					try { throw new FromCurrencyNotFoundInWalletException("FROM currency not found in user's wallet.");
					} catch (FromCurrencyNotFoundInWalletException e) {
						logger.warn("FromCurrencyNotFoundInWalletException: Transaction skipped for user: " + userName + ". From currency " + fromCurrency + " not found in wallet.", e);
					} continue;
					
				}
				if (!user.getWallet().containsCurrencyAndAmount(fromCurrency, amount)) {
					try { throw new InsufficientFromAmountException("User has insufficient balance in the FROM currency.");
					} catch (InsufficientFromAmountException e) {
						logger.warn("InsufficientFromAmountException: Transaction skipped for user: " + userName + ". Insufficient funds for " + fromCurrency + ".", e);
					} continue;
					
				}
				
				double exchangeRate = exchangeRates.getExchangeRate(fromCurrency, toCurrency);
				
				if (exchangeRate == 0.0) {
					logger.error("Transaction failed for user: " + userName + ". The FROM currency " + fromCurrency + " or TO currency " + toCurrency + " could not be found.");
					continue;	
				}
				
				if (fromCurrency.equalsIgnoreCase(toCurrency)) {
					logger.warn("Transaction skipped for user: " + userName + ". Converting the same exchange rates from: " + fromCurrency + " to " + toCurrency + ".");
					continue;	
				}
				
				double convertedAmount = converter.convert(fromCurrency, toCurrency, amount);
				user.getWallet().setWallet(fromCurrency, user.getWallet().getBalance(fromCurrency) - amount);
				user.getWallet().setWallet(toCurrency, user.getWallet().getBalance(toCurrency) + convertedAmount);
				logger.info("Transaction for user: " + userName + " was successful.");	
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			logger.error("Transaction file could not be found." + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error while reading the transaction file." + e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	 /**
     * Validates if the user with the specified name exists in the user list.
     *
     * @param userName The name of the user to validate.
     * @return The User object if found, otherwise null.
     */
	private User validateUser(String userName) {
		for (User user : users) {
			if (user.getName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	/**
     * Updates the users' data in a JSON file.
     *
     * @param updatedUsersFile The JSON file to update with the updated user data.
     * @param users            The list of users with their updated wallet information.
     */
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
