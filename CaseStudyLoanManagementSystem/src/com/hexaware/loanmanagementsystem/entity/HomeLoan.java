package com.hexaware.loanmanagementsystem.entity;

public class HomeLoan extends Loan {
	private String propertyAddress;
	private double propertyValue;

	public HomeLoan() {
		super();
	}

	public HomeLoan(String propertyAddress, double propertyValue) {
		super();
		this.propertyAddress = propertyAddress;
		this.propertyValue = propertyValue;
	}

	  public HomeLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm,
              LoanType loanType, LoanStatus loanStatus, String propertyAddress, double propertyValue) {
  super(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
  this.propertyAddress = propertyAddress;
  this.propertyValue = propertyValue; 
}

	public HomeLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm,
			LoanType loanTypeEnum, LoanType loanStatusEbum, String propertyAddress2, double propertyValue2) {
		// TODO Auto-generated constructor stub
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public double getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}

	@Override
	public String toString() {
		return "HomeLoan [propertyAddress=" + getPropertyAddress() + ", propertyValue=" + getPropertyValue() + ", getLoanId()="
				+ getLoanId() + ", getCustomer()=" + getCustomer().getCustomerID() + ", getPrincipalAmount()=" + getPrincipalAmount()
				+ ", getInteresetRate()=" + getInteresetRate() + ", getLoanTerm()=" + getLoanTerm() + ", getLoanType()="
				+ getLoanType() + ", getLoanStatus()=" + getLoanStatus() + "]";
	}
	
	
}
