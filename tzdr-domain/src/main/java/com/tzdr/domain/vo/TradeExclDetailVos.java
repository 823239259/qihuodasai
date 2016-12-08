package com.tzdr.domain.vo;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.tzdr.common.utils.ReadExclPOI;

@SuppressWarnings("serial")
public class TradeExclDetailVos implements Serializable{
	private String tradeDate;
	private String username;
	private String userNo;
	private String currencyNo;
	private String exchangeNo;
	private String commodityNo;
	private String buyNum;
	private String sellNum;
	private String tradePrice;
	private String free;
	private String orderType;
	private String orderUserno;
	private String orderUsername;
	private String tradeType;
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getCurrencyNo() {
		return currencyNo;
	}
	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	public String getExchangeNo() {
		return exchangeNo;
	}
	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	public String getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderUserno() {
		return orderUserno;
	}
	public void setOrderUserno(String orderUserno) {
		this.orderUserno = orderUserno;
	}
	public String getOrderUsername() {
		return orderUsername;
	}
	public void setOrderUsername(String orderUsername) {
		this.orderUsername = orderUsername;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public TradeExclDetailVos() {
	}
	public TradeExclDetailVos(String tradeDate, String username, String userNo, String currencyNo, String exchangeNo,
			String commodityNo, String buyNum, String sellNum, String tradePrice, String free, String orderType,
			String orderUserno, String orderUsername, String tradeType) {
		this.tradeDate = tradeDate;
		this.username = username;
		this.userNo = userNo;
		this.currencyNo = currencyNo;
		this.exchangeNo = exchangeNo;
		this.commodityNo = commodityNo;
		this.buyNum = buyNum;
		this.sellNum = sellNum;
		this.tradePrice = tradePrice;
		this.free = free;
		this.orderType = orderType;
		this.orderUserno = orderUserno;
		this.orderUsername = orderUsername;
		this.tradeType = tradeType;
	}
	
	@Override
	public String toString() {
		return "TradeExclDetailVos [tradeDate=" + tradeDate + ", username=" + username + ", userNo=" + userNo
				+ ", currencyNo=" + currencyNo + ", exchangeNo=" + exchangeNo + ", commodityNo=" + commodityNo
				+ ", buyNum=" + buyNum + ", sellNum=" + sellNum + ", tradePrice=" + tradePrice + ", free=" + free
				+ ", orderType=" + orderType + ", orderUserno=" + orderUserno + ", orderUsername=" + orderUsername
				+ ", tradeType=" + tradeType + "]";
	}
	public static void main(String[] args) {
		ReadExclPOI readExclPOI = new ReadExclPOI();
		try {
			List<TradeExclDetailVos> detailVos = (List<TradeExclDetailVos>)readExclPOI.readExcl2007("C:\\Users\\username\\Desktop\\成交单管理2016-12-02.xlsx", TradeExclDetailVos.class);
			for (int i = 0; i < detailVos.size(); i++) {
				System.out.println(detailVos.get(i).toString());
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
