package org.com1028.Coursework;
/*
 * Rio Viernes-Gonzales
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class OrdersDAO extends DBConnection {
	private List<OrderDetails> orderDetails = null;
	private TreeMap<Integer, Integer> orderNumsCustomerNums = null;
	private TreeMap<Integer, String> CDetails = null;

	public OrdersDAO() {
		super();
		  this.orderDetails = new ArrayList<OrderDetails>(); 
		  this.orderNumsCustomerNums = new TreeMap<Integer, Integer>(); 
		  this.CDetails = new TreeMap<Integer, String>();
		 
	}

	public TreeMap<Integer, Integer> getAllOrders() { 
		try {
			String q = "SELECT * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);

			while (rs.next()) {
				int orderNumber = rs.getInt("orderNumber");
				int customerNumber = rs.getInt("customerNumber");
				
				this.orderNumsCustomerNums.put(orderNumber, customerNumber);
			}

		} catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}

		return this.orderNumsCustomerNums;

	}

	public TreeMap<Integer, String> getAllCustomers() { 
		try {
			String query = "SELECT * from customers";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int customerNumber = rs.getInt("customerNumber");
				String customerName = rs.getString("customerName");
			
				this.CDetails.put(customerNumber, customerName);
			}
		} catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}


		return this.CDetails;
	}

	public List<OrderDetails> allOrderDetails() { 
		double value = 0.00;
		try {

			String query = "SELECT * from orderDetails";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int orderNumber = rs.getInt("orderNumber");
				String productCode = rs.getString("productCode");
				int quantityOrdered = rs.getInt("quantityOrdered");
				double priceEach = rs.getDouble("priceEach");
				int orderLineNumber = rs.getInt("orderLineNumber");
				//totalSpent = Math.round(totalSpent * 100.0 ) /100.0; 

					 this.orderDetails.add(new OrderDetails(orderNumber, productCode, quantityOrdered, priceEach,orderLineNumber, value));			
			}
		} catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		return this.orderDetails;
	}

	public String getOrdersOverTwentyFiveThousand() { 
		StringBuffer b = new StringBuffer();
		DecimalFormat df = new DecimalFormat("0.00");
		
		int orderNumber = 0;
		String name = null;
		double totalValue = 0.00;
		
		b.append("\n" + "Display the customer names and the order numbers where the order value is worth more than $25000: " + "\n");
		
		  for (Map.Entry<Integer, Integer> o : this.getAllOrders().entrySet()) {
		  for (Map.Entry<Integer, String> c : this.getAllCustomers().entrySet()) {
				  if (c.getKey() == o.getValue()) {
					  for (OrderDetails od : this.allOrderDetails()) { 
						  name = c.getValue();
						  orderNumber = o.getKey(); 
						  if (o.getKey()== od.getOrderNumber() ) { 
							  totalValue += od.getValue();
							  }
						  } 
					  }
				  if (totalValue > 25000.00) { 
					  b.append("\n" + "Customer Name: " + name);
					  b.append("\n" +"Order Number: " + orderNumber + "\t" + "Amount: $" + df.format(totalValue) + "\n");
					  }
				  totalValue = 0.00;
			  }
		  }
			return b.toString();
	}

}
