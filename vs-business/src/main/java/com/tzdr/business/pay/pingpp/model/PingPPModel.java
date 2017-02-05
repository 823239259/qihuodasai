package com.tzdr.business.pay.pingpp.model;

public class PingPPModel {
	private double amount;
	private String currency;
	private String subject;
	private String body;
	private String order_no;
	private String channel;
	private String client_ip;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	
	
	public PingPPModel(double amount, String currency, String subject, String body, String order_no, String channel,
			String client_ip) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.subject = subject;
		this.body = body;
		this.order_no = order_no;
		this.channel = channel;
		this.client_ip = client_ip;
	}
	public PingPPModel() {
	}
	
}
