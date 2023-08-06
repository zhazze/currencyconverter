package com.fdmgroup.currencyconverterproject.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Currency {

	    private String code;
	    private String alphaCode;
	    private String numericCode;
	    private String name;
	    private double rate;
	    private String date;
	    private double inverseRate;
		
	    @JsonCreator
	    public Currency(
	    		@JsonProperty("code") String code, 
	    		@JsonProperty("alphaCode") String alphaCode, 
	    		@JsonProperty("numericCode") String numericCode, 
	    		@JsonProperty("name") String name,
	    		@JsonProperty("rate") double rate, 
	    		@JsonProperty("date") String date, 
	    		@JsonProperty("inverseRate") double inverseRate) {
			super();
			this.code = code;
			this.alphaCode = alphaCode;
			this.numericCode = numericCode;
			this.name = name;
			this.rate = rate;
			this.date = date;
			this.inverseRate = inverseRate;
		}

		public String getCode() {
			return code;
		}

		public String getAlphaCode() {
			return alphaCode;
		}

		public String getNumericCode() {
			return numericCode;
		}

		public String getName() {
			return name;
		}

		public double getRate() {
			return rate;
		}

		public String getDate() {
			return date;
		}

		public double getInverseRate() {
			return inverseRate;
		}
	    
}