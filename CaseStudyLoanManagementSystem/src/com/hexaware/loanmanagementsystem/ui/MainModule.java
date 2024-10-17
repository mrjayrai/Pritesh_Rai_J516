package com.hexaware.loanmanagementsystem.ui;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.hexaware.loanmanagementsystem.entity.CarLoan;
import com.hexaware.loanmanagementsystem.entity.Customer;
import com.hexaware.loanmanagementsystem.entity.HomeLoan;
import com.hexaware.loanmanagementsystem.entity.Loan;
import com.hexaware.loanmanagementsystem.exception.InvalidLoanException;
import com.hexaware.loanmanagementsystem.service.ILoanService;
import com.hexaware.loanmanagementsystem.service.LoanServiceImpl;

public class MainModule {
	static Scanner scanner = new Scanner(System.in);

	public static int generateLoanID() {
		long timestamp = System.currentTimeMillis() / 1000;
		Random random = new Random();
		int randomNum = random.nextInt(900) + 100;
		long loanIdLong = timestamp % 100000 + randomNum;
		return (int) loanIdLong;
	}

	public static void main(String[] args) {
		ILoanService service = new LoanServiceImpl();
		MainModule client = new MainModule();
		boolean flag = true;
		try {

		
		while (flag) {
			System.out.println(
					"----------------------------------Welcome to Loan Management System----------------------------------");
			System.out.println("1 Apply for Loan");
			System.out.println("2 Calculate Interest");
			System.out.println("3 Check Loan Status");
			System.out.println("4 Calculate EMI");
			System.out.println("5 Loan Repayment");
			System.out.println("6 Check All Loans");
			System.out.println("7 Check Loan Detail");
			System.out.println("8 Exit");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				Loan loan = null;
				int loanId = client.generateLoanID();
				System.out.println("Please Enter your Customer ID: ");
				Customer customer = new Customer();
				int customerId = scanner.nextInt();
				customer.setCustomerID(customerId);
				System.out.println("Please Enter your Credit Score: ");
				int creditScore = scanner.nextInt();
				customer.setCustomerCreditScore(creditScore);
				System.out.println("Enter Principal Amount: ");
				double principalAmount = scanner.nextDouble();
				System.out.println("Enter Interest Rate: ");
				double interestRate = scanner.nextDouble();
				System.out.println("Enter Loan Term (in months): ");
				int loanTerm = scanner.nextInt();
				System.out.println("Enter Loan Type (HOMELOAN/CARLOAN): ");
				String loanType = scanner.next().toUpperCase();
				Loan.LoanType loanTypeEnum = Loan.LoanType.valueOf(loanType.toUpperCase());
				Loan.LoanStatus loanStatusEnum = Loan.LoanStatus.PENDING;
				switch (loanType) {
				case "HOMELOAN":
					System.out.println("Enter Property Address");
					String propertyAddress = scanner.next();
					System.out.println("Enter Property Value");
					double propertyValue = scanner.nextDouble();
					HomeLoan homeLoan = new HomeLoan(loanId, customer, principalAmount, interestRate, loanTerm,
							loanTypeEnum, loanStatusEnum, propertyAddress, propertyValue);

					try {
						service.applyLoan(homeLoan);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CARLOAN":
					System.out.println("Enter Car Model");
					String carModel = scanner.next();
					System.out.println("Enter Car Value");
					double carValue = scanner.nextDouble();
					CarLoan carLoan = new CarLoan(loanId, customer, principalAmount, interestRate, loanTerm,
							loanTypeEnum, loanStatusEnum, carModel, carValue);

					try {
						service.applyLoan(carLoan);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				default:
					break;
				}
//				System.out.println("Loan applied successfully with Loan ID: " + loan.getLoanId());
				break;

			case 2:
				System.out.println("Please Enter the Loan ID: ");
				int loanIDSec = scanner.nextInt();
				double calc = service.calculateEMI(loanIDSec);
				System.out.println("Calculated Interest: " + calc);
				break;

			case 3:
				System.out.println("Enter Loan ID: ");
				int loanIdInput = scanner.nextInt();
				service.loanStatus(loanIdInput);
				break;

			case 4:
				System.out.println("Enter Loan ID: ");
				int emiLoanId = scanner.nextInt();
				try {
					double emi = service.calculateEMI(emiLoanId);
					System.out.println("EMI for Loan ID " + emiLoanId + " is: " + emi);
				} catch (InvalidLoanException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 5:
				System.out.println("Enter Loan ID: ");
				int repaymentLoanId = scanner.nextInt();
				System.out.println("Enter Amount to Repay: ");
				double amount = scanner.nextDouble();
				try {
					service.loanRepayment(repaymentLoanId, amount);
					System.out.println("Repayment successful for Loan ID: " + repaymentLoanId);
				} catch (InvalidLoanException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 6:
				System.out.println("List of all loans:");
				List<Loan> loanList = service.getAllLoan();
				for (Loan l : loanList) {
					Loan.LoanType loanTypeRec = l.getLoanType();
					switch (loanTypeRec) {
					case HOMELOAN:
						HomeLoan loanSub = (HomeLoan) l;
						System.out.println(loanSub.toString());
						break;
					case CARLOAN:
						CarLoan loanCar = (CarLoan) l;
						System.out.println(loanCar.toString());
						break;
					}
				}
				break;

			case 7:
				System.out.println("Enter Loan ID: ");
				int detailLoanId = scanner.nextInt();
				try {
					Loan loanDetail = service.getLoanById(detailLoanId);
					if (loanDetail != null) {
						System.out.println("Loan Details: " + loanDetail);
					} else {
						System.out.println("Loan not found.");
					}
				} catch (InvalidLoanException e) {
					// TODO: handle exception
					System.err.println("Loan not found");
				}
				break;

			case 8:
				flag = false; // Exit the loop
				System.out.println("Thank you for using the Loan Management System. Goodbye!");
				break;

			default:
				System.out.println("Invalid choice, please try again.");
				break;
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Please enter valid input");
		}
		scanner.close(); // Close the scanner resource
	}
}
