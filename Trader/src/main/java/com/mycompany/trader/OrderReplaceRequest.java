package com.mycompany.trader;

import java.io.Serializable;


public class OrderReplaceRequest implements Serializable{
	
	private String uuid;
	private String symbol;
	private double price;
	private double otherPrice;
	private char side;
	private double qty;
	private String quoteID;
	private String userID;
	private String accountID;
	private String currency;
	private char contingencyType;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public char getSide() {
		return side;
	}
	public void setSide(char side) {
		this.side = side;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getQuoteID() {
		return quoteID;
	}
	public void setQuoteID(String quoteID) {
		this.quoteID = quoteID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public char getContingencyType() {
		return contingencyType;
	}
	public void setContingencyType(char contingencyType) {
		this.contingencyType = contingencyType;
	}
	public double getOtherPrice() {
		return otherPrice;
	}
	public void setOtherPrice(double otherPrice) {
		this.otherPrice = otherPrice;
	}
}
