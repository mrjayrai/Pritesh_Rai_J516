package com.hexaware.loanmanagementsystem.entity;

public class CarLoan extends Loan {
	private String carModel;
	private double carValue;

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		carModel = carModel;
	}

	public double getCarValue() {
		return carValue;
	}

	public void setCarValue(int carValue) {
		carValue = carValue;
	}

	public CarLoan(String carModel, int carValue) {
		super();
		carModel = carModel;
		carValue = carValue;
	}

	public CarLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, LoanType loanStatusEbum, String carModel2, double carValue2) {
		super();
	}

	public CarLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm,
            LoanType loanType, LoanStatus loanStatus, String carModel, double carValue) {
 super(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
 this.carModel = carModel; // Initialize carModel
 this.carValue = carValue; // Initialize carValue
}

	@Override
	public String toString() {
		return "CarLoan [CarModel=" + carModel + ", CarValue=" + carValue + ", getLoanId()=" + getLoanId()
				+ ", getCustomer()=" + getCustomer().getCustomerName() + ", getPrincipalAmount()=" + getPrincipalAmount()
				+ ", getInteresetRate()=" + getInteresetRate() + ", getLoanTerm()=" + getLoanTerm() + ", getLoanType()="
				+ getLoanType() + ", getLoanStatus()=" + getLoanStatus() +  "]";
	}

	
}
