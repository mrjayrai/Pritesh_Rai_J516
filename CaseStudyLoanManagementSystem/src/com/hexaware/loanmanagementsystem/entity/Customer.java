package com.hexaware.loanmanagementsystem.entity;

public class Customer {
	private int customerID;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String customerAddress;
	private int customerCreditScore;
	
	
	
	public Customer() {
		super();
	}



	public Customer(int customerID, String customerName, String customerEmail, String customerPhone,
			String customerAddress, int customerCreditScore) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.customerCreditScore = customerCreditScore;
	}



	public int getCustomerID() {
		return customerID;
	}



	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCustomerEmail() {
		return customerEmail;
	}



	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}



	public String getCustomerPhone() {
		return customerPhone;
	}



	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}



	public String getCustomerAddress() {
		return customerAddress;
	}



	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}



	public int getCustomerCreditScore() {
		return customerCreditScore;
	}



	public void setCustomerCreditScore(int customerCreditScore) {
		this.customerCreditScore = customerCreditScore;
	}
	
	
	
}
