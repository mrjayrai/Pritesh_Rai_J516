package com.hexaware.loanmanagementsystem.service;

import java.util.List;

import com.hexaware.loanmanagementsystem.entity.Loan;
import com.hexaware.loanmanagementsystem.exception.InvalidLoanException;

public interface ILoanService {
	void applyLoan(Loan loan) throws Exception;

	double calculateInterest(int loanId) throws InvalidLoanException;


	void loanStatus(int loanId) throws InvalidLoanException;

	double calculateEMI(int loanId) throws InvalidLoanException;


	void loanRepayment(int loanId, double amount) throws InvalidLoanException;

	List<Loan> getAllLoan();

	Loan getLoanById(int loanId) throws InvalidLoanException;
}
