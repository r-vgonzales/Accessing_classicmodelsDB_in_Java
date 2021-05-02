package org.com1028.Coursework;
/*
 * Rio Viernes-Gonzales
 */
public class Payments {
	 private int customerNumber = 0;
	 private String checkNumber = null;
	 private String paymentDate = null;
	 private double amount = 0.00;
	 
	public Payments(int customerNumber, String checkNumber, String paymentDate, double amount) {
		super();
		this.customerNumber = customerNumber;
		this.checkNumber = checkNumber;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public double getAmount() {
		return amount;
	}
	 
}
