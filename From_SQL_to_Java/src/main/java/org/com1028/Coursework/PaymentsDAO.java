package org.com1028.Coursework;
/*
 * @author Rio Viernes-Gonzales
 */
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentsDAO extends DBConnection {
 private List<Payments>  bigAmounts = null;
 private List<Double> total = null;
 private double twiceAv = 0.00;

 
	public PaymentsDAO() {
		super();
		this.bigAmounts = new ArrayList<Payments>();
		this.total = new ArrayList<Double>();
		this.twiceAv = twiceAv;
	}
	
	/*
	 * calculates the average 
	 */
	  public double tAverage() { 
		  double avg = 0.00; 
		  double sum = 0.00; 
		  try {
			  String sql1 = "SELECT amount FROM payments"; 
			  Statement stmt = con.createStatement(); 
			  ResultSet rs = stmt.executeQuery(sql1);
	  
			  while(rs.next()) { 
				  double amount = rs.getDouble("amount");
				  this.total.add(amount); 
				  sum += amount; 
				  avg = sum/this.total.size();
				  this.twiceAv = Math.round(2 *avg  * 1000000d) / 1000000d;; 
				  }
	  } 
		  catch (SQLException e) {
	  System.out.println("Error while retrieving records."); 
	  throw new RuntimeException(e); 
	  }
		 
		  return this.twiceAv;
	}
	/*
	 * if the amount is greater than twiceAv, then the object is stored
	 */
	 
	public List<Payments> allBigPayments()  {
		try {
			String sql = "SELECT * FROM payments";	
	
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())  {
				int customerNumber = rs.getInt("customerNumber");
		    	String checkNumber = rs.getString("checkNumber");
		    	String paymentDate = rs.getString("paymentDate");
		    	double amount = rs.getDouble("amount");
		    	
			    	if (amount > this.twiceAv )  {  //64863.00
			    		this.bigAmounts.add(new Payments(customerNumber, checkNumber, paymentDate, amount));
			  
			}
		}
		}
		catch (SQLException e)  {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
	return this.bigAmounts;
	}
	
	/*
	 * Will display the payments that are larger than twice the average of all payments
	 */
	public String displayBigPayments()  {
		 DecimalFormat f = new DecimalFormat("0.00");
		 StringBuffer b = new StringBuffer();
		 	b.append("Display the details of payments that are larger than twice the average of all the payments: ");
		 	b.append("\n");
		 
		 for (Payments payment : this.allBigPayments())  {
			 b.append("\n" + "Customer Number: " + payment.getCustomerNumber() + "\n");
			 b.append("Check Number: " + payment.getCheckNumber() + "\n");
			 b.append("Payment Date: " + payment.getPaymentDate() + "\n");
			 b.append("Amount: $" + f.format(payment.getAmount()) + "\n");
		 }
		 return b.toString();
	}
		 
	}
