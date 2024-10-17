package com.hexaware.loanmanagementsystem.service;

import java.util.List;

import com.hexaware.loanmanagementsystem.dao.ILoanRepository;
import com.hexaware.loanmanagementsystem.dao.ILoanRepositoryImpl;
import com.hexaware.loanmanagementsystem.entity.Loan;
import com.hexaware.loanmanagementsystem.exception.InvalidLoanException;

public class LoanServiceImpl implements ILoanService {
	
	private ILoanRepository dao;
	
	
	public LoanServiceImpl() {
		super();
		dao = new ILoanRepositoryImpl();
	}

	@Override
	public void applyLoan(Loan loan) {
		// TODO Auto-generated method stub
		try {
			dao.applyLoan(loan);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		
		return dao.calculateInterest(loanId);
	}

	

	@Override
	public void loanStatus(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		dao.loanStatus(loanId);
		
	}

	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		return dao.calculateEMI(loanId);
	}

	

	@Override
	public void loanRepayment(int loanId, double amount) throws InvalidLoanException {
		// TODO Auto-generated method stub
		dao.loanRepayment(loanId, amount);
		
	}

	@Override
	public List<Loan> getAllLoan() {
		// TODO Auto-generated method stub
		return dao.getAllLoan();
	}

	@Override
	public Loan getLoanById(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		return dao.getLoanById(loanId);
	}
	
}
