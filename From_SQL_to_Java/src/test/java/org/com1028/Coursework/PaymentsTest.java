package org.com1028.Coursework;
/*
* @author Rio Viernes-Gonzales
*/

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class PaymentsTest extends DBConnection {

	/*
	 * Calculates 2*average of all payments.
	 */
	public double getCorrectAverage()  {
		double avg = 0.00;
		double twiceAvg = 0.00;

		try  {
			String q = "SELECT 2*avg(amount) FROM PAYMENTS"; 
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(q);
			
			while (rs.next())  {
				avg = rs.getDouble("2*avg(amount)");  
				twiceAvg = avg;
			}
		}
		catch (SQLException e)  {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		
		return twiceAvg;
	}
	
		@Test
	  public void testgetCorrectAverage() { 
		  PaymentsDAO paymentsDAO = new PaymentsDAO(); 
		  paymentsDAO.openConnection();
		  assertEquals(paymentsDAO.tAverage(), this.getCorrectAverage(), 0); //64863.291062
		  paymentsDAO.closeConn(); 
		  }
	
	/*
	 * Stores all the details of the payments where the amount is more than twice the average, into an ArrayList
	 */
		
	public List<Payments> getCorrectLargePayments()  {
		List<Payments> correctLargePayments = new ArrayList<Payments>();
		
		try  {
			String q = "select * from payments where amount > (select 2*avg(amount) from payments)";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			
			while (rs.next())  {
				int customerNumber = rs.getInt("customerNumber");
				String checkNumber = rs.getString("checkNumber");
				String paymentDate =  rs.getString("paymentDate");
				double amount = rs.getDouble("amount");
				
				correctLargePayments.add(new Payments(customerNumber, checkNumber, paymentDate, amount));
			}
		}
		catch (SQLException e)  {
			System.out.println("Error while retrieving records.");
		}
		
		return correctLargePayments;
	}
	
	@Test
	public void testGetCorrectLargePayments()  {
		PaymentsDAO paymentsDAO = new PaymentsDAO();
		paymentsDAO.openConnection();
		List<Payments> realLargeAmounts = paymentsDAO.allBigPayments();
		
		for (int i = 0; i < this.getCorrectLargePayments().size(); i++)  {
			assertEquals(realLargeAmounts.get(i).getCustomerNumber(), this.getCorrectLargePayments().get(i).getCustomerNumber());
			assertEquals(realLargeAmounts.get(i).getCheckNumber(), this.getCorrectLargePayments().get(i).getCheckNumber());
			assertEquals(realLargeAmounts.get(i).getPaymentDate(), this.getCorrectLargePayments().get(i).getPaymentDate());
			assertEquals(realLargeAmounts.get(i).getAmount(), this.getCorrectLargePayments().get(i).getAmount(), 0);
		}
		paymentsDAO.closeConn();
	}
	
	@Test
	public void testDisplayLargePayments()  {
		PaymentsDAO paymentsDAO = new PaymentsDAO();
		DecimalFormat f = new DecimalFormat("0.00");
		 StringBuffer b = new StringBuffer();
		 	b.append("Display the details of payments that are larger than twice the average of all the payments: ");
		 	b.append("\n");
		 
		 for (Payments p : this.getCorrectLargePayments())  {
			 b.append("\n" + "Customer Number: " + p.getCustomerNumber() + "\n");
			 b.append("Check Number: " + p.getCheckNumber() + "\n");
			 b.append("Payment Date: " + p.getPaymentDate() + "\n");
			 b.append("Amount: $" + f.format(p.getAmount()) + "\n");
		 }
		 
		 paymentsDAO.openConnection();
		 assertEquals(b.toString(), paymentsDAO.displayBigPayments());
		 paymentsDAO.closeConn();
	}
}