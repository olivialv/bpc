package com.mycompany.trader;
/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PortfolioPosition {

    private String orderId;

    private String userId;

    private String symbol;

    private int size;

    private boolean isBuy;
    // true for buy, false for sell

    private long timestamp;

    private double price;

    private long updateTime;

    private boolean isClosed;
	// use to flag trade as successfully closed
    
    private Double stopPrice;

    public Double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(Double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public Double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(Double limitPrice) {
        this.limitPrice = limitPrice;
    }
    
    private Double limitPrice;

    public PortfolioPosition(String orderId, String symbol, int size, boolean isBuy, double price, boolean isClosed) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.size = size;
        this.isBuy = isBuy;
        this.price = price;
        this.isClosed = isClosed;
        this.updateTime = System.currentTimeMillis();
    }

    public PortfolioPosition() {
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean getIsBuy() {
        return this.isBuy;
    }

    public void setIsBuy(boolean isBuy) {
        this.isBuy = isBuy;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PortfolioPosition [orderId=" + this.orderId + ", symbol="
                + this.symbol + ", size=" + this.size + ", isBuy=" + this.isBuy
                + ", price=" + this.price + ", isClosed=" + this.isClosed + ""
                + "Stop price= " +this.stopPrice + " Limit Price= "+ this.limitPrice+"]";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
}
