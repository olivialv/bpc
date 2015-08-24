/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trader;

/**
 *
 * @author OliviaLu
 */

import java.io.Serializable;

public class EditFilledOrderRequest implements Serializable {

	private String tradeId;
	
	private String userId;
	
	private char side;
	
	private String currency;

	private int qty;
	
	private String symbol;
	
	private Double newStopPrice;
	private Double newLimitPrice;
	
	private Double oldStopPrice;
	private Double oldLimitPrice;
	
	public EditFilledOrderRequest(){}

	public Double getNewStopPrice() {
		return newStopPrice;
	}

	public void setNewStopPrice(double newStopPrice) {
		this.newStopPrice = newStopPrice;
	}

	public Double getNewLimitPrice() {
		return newLimitPrice;
	}

	public void setNewLimitPrice(double newLimitPrice) {
		this.newLimitPrice = newLimitPrice;
	}

	public Double getOldStopPrice() {
		return oldStopPrice;
	}

	public void setOldStopPrice(Double oldStopPrice) {
		this.oldStopPrice = oldStopPrice;
	}

	public Double getOldLimitPrice() {
		return oldLimitPrice;
	}

	public void setOldLimitPrice(Double oldLimitPrice) {
		this.oldLimitPrice = oldLimitPrice;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public char getSide() {
		return side;
	}

	public void setSide(char side) {
		this.side = side;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

