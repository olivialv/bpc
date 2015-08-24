package com.mycompany.trader;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TradeReport {

	private String uuid;
	private String openTime;
	private String closeTime;
	private String symbol;
	private int size;
	private char side;
	private double openPrice;
	private double closePrice;
	private double finalPnL;
	
	public TradeReport(){ }
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public char getSide() {
		return side;
	}
	public void setSide(char side) {
		this.side = side;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getFinalPnL() {
		return finalPnL;
	}
	public void setFinalPnL(double finalPnL) {
		this.finalPnL = finalPnL;
	}
}
