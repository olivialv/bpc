package com.mycompany.trader;

import java.io.Serializable;


public class MarketOrderRequest implements Serializable {
	
	private String uuid;
	private String symbol;
	private double price;
	private char side;
	private int qty;
	private String quoteID;
	private String userID;
	private String accountID;
	private String currency;
	private Double stopLoss;
	private Double takeProfit;
	private String timestamp;
	private Double otherPrice;

    public Double getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(Double otherPrice) {
        this.otherPrice = otherPrice;
    }
	
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
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
	public Double getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(Double stopLoss) {
		this.stopLoss = stopLoss;
	}
	public Double getTakeProfit() {
		return takeProfit;
	}
	public void setTakeProfit(Double takeProfit) {
		this.takeProfit = takeProfit;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}