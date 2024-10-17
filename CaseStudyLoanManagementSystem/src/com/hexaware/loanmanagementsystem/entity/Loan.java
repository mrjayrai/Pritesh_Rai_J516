package com.hexaware.loanmanagementsystem.entity;

public class Loan {
	private int loanId;
	private Customer customer;
	private double principalAmount;
	private double interesetRate;
	private int loanTerm;
	private LoanType loanType;
	private LoanStatus loanStatus;
	
	public enum LoanType{
		CARLOAN,HOMELOAN
	};
	
	public enum LoanStatus{
		PENDING,APPROVED
	}

	public Loan() {
		super();
	}

	public Loan(int loanId, Customer customer, double principalAmount, double interesetRate, int loanTerm,
			LoanType loanType, LoanStatus loanStatus) {
		super();
		this.loanId = loanId;
		this.customer = customer;
		this.principalAmount = principalAmount;
		this.interesetRate = interesetRate;
		this.loanTerm = loanTerm;
		this.loanType = loanType;
		this.loanStatus = loanStatus;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public double getInteresetRate() {
		return interesetRate;
	}

	public void setInteresetRate(double interesetRate) {
		this.interesetRate = interesetRate;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", customer=" + customer.getCustomerName() + ", principalAmount=" + principalAmount
				+ ", interesetRate=" + interesetRate + ", loanTerm=" + loanTerm + ", loanType=" + loanType
				+ ", loanStatus=" + loanStatus + "]";
	};
	
	
	
}
