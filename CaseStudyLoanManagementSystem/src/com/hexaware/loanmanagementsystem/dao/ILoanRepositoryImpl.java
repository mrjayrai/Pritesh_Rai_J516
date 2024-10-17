package com.hexaware.loanmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.loanmanagementsystem.entity.CarLoan;
import com.hexaware.loanmanagementsystem.entity.Customer;
import com.hexaware.loanmanagementsystem.entity.HomeLoan;
import com.hexaware.loanmanagementsystem.entity.Loan;
import com.hexaware.loanmanagementsystem.exception.InvalidLoanException;
import com.hexaware.loanmanagementsystem.util.DBUtil;

public class ILoanRepositoryImpl implements ILoanRepository {

	private Connection conn;

	public ILoanRepositoryImpl() {
		super();
		conn = DBUtil.getConnection();
	}

	@Override
	public void applyLoan(Loan loan) throws Exception {
		// TODO Auto-generated method stub
		String insertLoanQuery = "insert into loan (loan_id,customer_id,principal_amount,interest_rate,loan_term,loan_type,loan_status)"
				+ " values (?,?,?,?,?,?,?);";
		PreparedStatement insertLoanQueryStmt = conn.prepareStatement(insertLoanQuery);
		if (loan.getCustomer().getCustomerCreditScore() > 650) {
			insertLoanQueryStmt.setInt(1, loan.getLoanId());
			insertLoanQueryStmt.setInt(2, loan.getCustomer().getCustomerID());
			insertLoanQueryStmt.setDouble(3, loan.getPrincipalAmount());
			insertLoanQueryStmt.setDouble(4, loan.getInteresetRate());
			insertLoanQueryStmt.setInt(5, loan.getLoanTerm());
			insertLoanQueryStmt.setString(6, loan.getLoanType().name());
			insertLoanQueryStmt.setString(7, "APPROVED");
		} else {
			insertLoanQueryStmt.setInt(1, loan.getLoanId());
			insertLoanQueryStmt.setInt(2, loan.getCustomer().getCustomerID());
			insertLoanQueryStmt.setDouble(3, loan.getPrincipalAmount());
			insertLoanQueryStmt.setDouble(4, loan.getInteresetRate());
			insertLoanQueryStmt.setInt(5, loan.getLoanTerm());
			insertLoanQueryStmt.setString(6, loan.getLoanType().name());
			insertLoanQueryStmt.setString(7, "PENDING");
		}

		int count = insertLoanQueryStmt.executeUpdate();
		if (count > 0) {
			if (loan instanceof HomeLoan) {
				HomeLoan homeLoan = (HomeLoan) loan;
				String homeLoanInsertQUery = "INSERT INTO homeloan (home_loan_id, loan_id, property_address, property_value) "
						+ "VALUES (?, ?, ?, ?);";
				PreparedStatement homeLoanStmt = conn.prepareStatement(homeLoanInsertQUery);
				homeLoanStmt.setInt(1, homeLoan.getLoanId());
				homeLoanStmt.setInt(2, homeLoan.getLoanId());
				homeLoanStmt.setString(3, homeLoan.getPropertyAddress());
				homeLoanStmt.setDouble(4, homeLoan.getPropertyValue());
				homeLoanStmt.executeUpdate();
				System.out.println("Home Loan Added Successfully: " + homeLoan.getLoanId());
			}
			if (loan instanceof CarLoan) {
				CarLoan carLoan = (CarLoan) loan;
				String carLoanInsertQUery = "INSERT INTO carloan (car_loan_id, loan_id, car_model, car_value) "
						+ "VALUES (?, ?, ?, ?);";
				PreparedStatement homeLoanStmt = conn.prepareStatement(carLoanInsertQUery);
				homeLoanStmt.setInt(1, carLoan.getLoanId());
				homeLoanStmt.setInt(2, carLoan.getLoanId());
				homeLoanStmt.setString(3, carLoan.getCarModel());
				homeLoanStmt.setDouble(4, carLoan.getCarValue());
				homeLoanStmt.executeUpdate();
				System.out.println("Car Loan Added Successfully: " + carLoan.getLoanId());
			}
		} else {
			System.err.println("Loan application failed ");
		}
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		String fetchLoan = "Select * from loan where loan_id =?;";
		PreparedStatement fetchLoanStmt;
		try {
			fetchLoanStmt = conn.prepareStatement(fetchLoan);
			fetchLoanStmt.setInt(1, loanId);
			ResultSet resultSet = fetchLoanStmt.executeQuery();

			if (resultSet.next()) {
				double principal = resultSet.getDouble("principal_amount");
				double interestRate = resultSet.getDouble("interest_rate");
				int loanTenureMonths = resultSet.getInt("loan_term");
				double interest = (principal * (interestRate / 100) * loanTenureMonths) / 12;

				return interest;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("Invalid Loan ID");
		}

		return 0;
	}

	@Override
	public void loanStatus(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		String fetchStatus = "select loan_status from loan where loan_id = ?";
		try {
			PreparedStatement fetchStatusStmt = conn.prepareStatement(fetchStatus);
			fetchStatusStmt.setInt(1, loanId);
			ResultSet resultSet = fetchStatusStmt.executeQuery();

			if (resultSet.next()) {
				String loanStatus = resultSet.getString("loan_status");
				System.out.println("Loan Status: " + loanStatus);
			} else {
				throw new InvalidLoanException();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("Invalid Loan ID provided: " + loanId);
		}

	}

	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		String fetchLoanQuery = "SELECT principal_amount, interest_rate, loan_term FROM loan WHERE loan_id = ?";
		try {
			PreparedStatement fetchLoanStmt = conn.prepareStatement(fetchLoanQuery);
			fetchLoanStmt.setInt(1, loanId);
			ResultSet resultSet = fetchLoanStmt.executeQuery();
			if (resultSet.next()) {
				double principalAmount = resultSet.getDouble("principal_amount");
				double annualInterestRate = resultSet.getDouble("interest_rate");
				int loanTermMonths = resultSet.getInt("loan_term");

				double monthlyInterestRate = (annualInterestRate / 12) / 100;

				double emi;
				if (monthlyInterestRate != 0) {
					emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths))
							/ (Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1);
				} else {
					emi = principalAmount / loanTermMonths;
				}

				return emi;
			}else {
				throw new InvalidLoanException();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("Invalid Loan ID entered: "+loanId);
		}
		return 0;
	}

	@Override
	public void loanRepayment(int loanId, double amount) throws InvalidLoanException {
		// TODO Auto-generated method stub
		String fetchLoanQuery = "SELECT principal_amount, interest_rate, loan_term FROM loan WHERE loan_id = ?";
		try {
			PreparedStatement fetchLoanStmt = conn.prepareStatement(fetchLoanQuery);
			fetchLoanStmt.setInt(1, loanId);
			ResultSet resultSet = fetchLoanStmt.executeQuery();
			if (resultSet.next()) {
				double principalAmount = resultSet.getDouble("principal_amount");
				double annualInterestRate = resultSet.getDouble("interest_rate");
				int loanTermMonths = resultSet.getInt("loan_term");

				double monthlyInterestRate = (annualInterestRate / 12) / 100;

				double emi;
				if (monthlyInterestRate != 0) {
					emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths))
							/ (Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1);
				} else {
					emi = principalAmount / loanTermMonths;
				}

				if (amount < emi) {
	                System.err.println("Payment rejected: Amount is less than a single EMI.");
	                return;
	            }
				
				int noOfEMIs = (int)(amount / emi);
	            System.out.println("You can pay " + noOfEMIs + " EMI(s) with the amount provided.");

			}else {
				throw new InvalidLoanException();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("Invalid Loan ID entered: "+loanId);
		}

	}

	@Override
	public List<Loan> getAllLoan() {
	    List<Loan> loans = new ArrayList<>();
	    String fetchLoansQuery = "SELECT loan.loan_id, loan.customer_id, loan.principal_amount, "
	            + "loan.interest_rate, loan.loan_term, loan.loan_type, loan.loan_status, "
	            + "homeloan.property_address, homeloan.property_value, "
	            + "carloan.car_model, carloan.car_value "
	            + "FROM loan "
	            + "LEFT JOIN homeloan ON loan.loan_id = homeloan.loan_id "
	            + "LEFT JOIN carloan ON loan.loan_id = carloan.loan_id";

	    try  {
	    	PreparedStatement fetchLoansStmt = conn.prepareStatement(fetchLoansQuery);
	         ResultSet resultSet = fetchLoansStmt.executeQuery();

	        while (resultSet.next()) {
	            int loanId = resultSet.getInt("loan.loan_id");
	            Customer customer = new Customer();
	            int customerId = resultSet.getInt("customer_id");
	            customer.setCustomerID(customerId);
	            double principalAmount = resultSet.getDouble("principal_amount");
	            double interestRate = resultSet.getDouble("interest_rate");
	            int loanTerm = resultSet.getInt("loan_term");
	            String loanType = resultSet.getString("loan_type");
	            String loanStatus = resultSet.getString("loan_status");
	            Loan.LoanStatus loanStatusEbum = Loan.LoanStatus.valueOf(loanStatus.toUpperCase());
	            Loan.LoanType loanTypeEnum = Loan.LoanType.valueOf(loanType.toUpperCase());
	            
	            if("HOMELOAN".equals(loanType)) {
	            	String propertyAddress = resultSet.getString("property_address");
	                double propertyValue = resultSet.getDouble("property_value");
	                HomeLoan homeLoan = new HomeLoan(loanId, customer, principalAmount, interestRate, loanTerm, loanTypeEnum, loanStatusEbum, propertyAddress, propertyValue);
	                loans.add(homeLoan);
	            }else if("CARLOAN".equals(loanType)) {
	            	String carModel = resultSet.getString("car_model");
	                double carValue = resultSet.getDouble("car_value");
//	                CarLoan carLoan = new CarLoan(loanId, customer, principalAmount, interestRate, loanTerm, loanStatusEbum, carModel, carValue);
	                CarLoan carLoan = new CarLoan(loanId, customer, principalAmount, interestRate, loanTerm, loanTypeEnum, loanStatusEbum, carModel, carValue);
	                loans.add(carLoan);
	                
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return loans;
	}


	@Override
	public Loan getLoanById(int loanId) throws InvalidLoanException {
	    Loan loan = null;
	    String fetchLoansQuery = "SELECT loan.loan_id, loan.customer_id, loan.principal_amount, "
	            + "loan.interest_rate, loan.loan_term, loan.loan_type, loan.loan_status, "
	            + "homeloan.property_address, homeloan.property_value, "
	            + "carloan.car_model, carloan.car_value "
	            + "FROM loan "
	            + "LEFT JOIN homeloan ON loan.loan_id = homeloan.loan_id "
	            + "LEFT JOIN carloan ON loan.loan_id = carloan.loan_id "
	            + "WHERE loan.loan_id = ?";

	    try {
	        PreparedStatement fetchLoansStmt = conn.prepareStatement(fetchLoansQuery);
	        fetchLoansStmt.setInt(1, loanId); // Set the loanId parameter
	        ResultSet resultSet = fetchLoansStmt.executeQuery();

	        if (resultSet.next()) { // Use if instead of while since you expect a single loan
	            Customer customer = new Customer(); // Initialize customer
	            int customerId = resultSet.getInt("customer_id");
	            customer.setCustomerID(customerId);
	            
	            double principalAmount = resultSet.getDouble("principal_amount");
	            double interestRate = resultSet.getDouble("interest_rate");
	            int loanTerm = resultSet.getInt("loan_term");
	            String loanType = resultSet.getString("loan_type");
	            String loanStatus = resultSet.getString("loan_status");
	            
	            Loan.LoanType loanTypeEnum = Loan.LoanType.valueOf(loanType.toUpperCase());
	            Loan.LoanStatus loanStatusEnum = Loan.LoanStatus.valueOf(loanStatus.toUpperCase());
	            
	            if ("HOMELOAN".equals(loanType)) {
	                String propertyAddress = resultSet.getString("property_address");
	                double propertyValue = resultSet.getDouble("property_value");
	                loan = new HomeLoan(loanId, customer, principalAmount, interestRate, loanTerm, loanTypeEnum, loanStatusEnum, propertyAddress, propertyValue);
	            } else if ("CARLOAN".equals(loanType)) {
	                String carModel = resultSet.getString("car_model");
	                double carValue = resultSet.getDouble("car_value");
	                loan = new CarLoan(loanId, customer, principalAmount, interestRate, loanTerm, loanTypeEnum, loanStatusEnum, carModel, carValue);
	            }
	        } else {
	            throw new InvalidLoanException();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return loan;
	}
}

