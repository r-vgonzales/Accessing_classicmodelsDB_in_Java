package org.com1028.Coursework;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class OrdersTest extends DBConnection{

	public TreeMap<Integer, Integer> getAllCorrectOrders()  {
		TreeMap<Integer, Integer> correctAllOrders = new TreeMap<Integer, Integer>();
		try  {
			String q = "SELECT orderNumber, customerNumber from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);

			while (rs.next()) {
				int orderNumber = rs.getInt("orderNumber");
				int customerNumber = rs.getInt("customerNumber");
				
				correctAllOrders.put(orderNumber, customerNumber);
		}
		}
		catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		return correctAllOrders;
	}
	
	@Test
	public void testAllOrders()  {
		OrdersDAO ordersDAO = new OrdersDAO();
		ordersDAO.openConnection();
		assertEquals(ordersDAO.getAllOrders(), this.getAllCorrectOrders());
		ordersDAO.closeConn();
	}
	
	public TreeMap<Integer, String> getAllCorrectCustomers()  {
		//List<Customers> correctAllCustomers = new ArrayList<Customers>();
		TreeMap<Integer, String> correctAllCustomers = new TreeMap<Integer, String>();
		try  {
			String q = "SELECT * from customers";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);

			while (rs.next()) {
				int customerNumber = rs.getInt("customerNumber");
				String customerName = rs.getString("customerName");
				/*
				 * String contactLastName = rs.getString("contactLastName"); String
				 * contactFirstName = rs.getString("contactFirstName"); String phone =
				 * rs.getString("phone"); String addressLine1 = rs.getString("addressLine1");
				 * String addressLine2 = rs.getString("addressLine2"); String city =
				 * rs.getString("city"); String state = rs.getString("state"); String postalCode
				 * = rs.getString("postalCode"); String country = rs.getString("country"); int
				 * salesRepEmployeeNumber = rs.getInt("salesRepEmployeeNumber"); Double
				 * creditLimit = rs.getDouble("creditLimit");
				 */
				
				/*
				 * correctAllCustomers.add(new Customers(customerNumber, customerName,
				 * contactLastName, contactFirstName, phone, addressLine1, addressLine2, city,
				 * state, postalCode, country, salesRepEmployeeNumber, creditLimit));
				 */
				correctAllCustomers.put(customerNumber, customerName);
		}
		}
		catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		return correctAllCustomers;
	}
	
	@Test
	public void testAllCustomers()  {
		OrdersDAO ordersDAO = new OrdersDAO();
		ordersDAO.openConnection();
		assertEquals(ordersDAO.getAllCustomers().size(), this.getAllCorrectCustomers().size());
		ordersDAO.closeConn();
	}
	
	public List<OrderDetails> getAllOrderDetails()  {
		List<OrderDetails> allOrderDetails = new ArrayList<OrderDetails>();
		double value = 0.00;
		try {

			String query = "select * from orderDetails";
					//"select orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber, sum(quantityOrdered*priceEach) from orderDetails group by quantityOrdered,priceEach";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				int orderNumber = rs.getInt("orderNumber");
				String productCode = rs.getString("productCode");
				int quantityOrdered = rs.getInt("quantityOrdered");
				double priceEach = rs.getDouble("priceEach");
				int orderLineNumber = rs.getInt("orderLineNumber");
				//double totalSpent = rs.getDouble("sum(quantityOrdered*priceEach)");
				//totalSpent = Math.round(quantityOrdered * priceEach * 100.0) / 100.0;
				
				allOrderDetails.add(new OrderDetails(orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber, value));
	}
		}
		catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		return allOrderDetails;
	}
	
	@Test
	public void testODSize()  {
		OrdersDAO ordersDAO = new OrdersDAO();
		ordersDAO.openConnection();
		assertEquals(ordersDAO.allOrderDetails().size(), this.getAllOrderDetails().size());
		ordersDAO.closeConn();
	}
	
	@Test
	public void testOrdersOverTwentyFiveThousand()  {
		OrdersDAO ordersDAO = new OrdersDAO();
		ordersDAO.openConnection();

		StringBuffer b = new StringBuffer();
		DecimalFormat df = new DecimalFormat("0.00");
		
		b.append("\n" + "Display the customer names and the order numbers where the order value is worth more than $25000: " + "\n");

		try  {
			String q = "select c.customerName, orders.orderNumber, sum(quantityOrdered*priceEach)" +
					"from ((orders inner join orderDetails od on orders.orderNumber = od.orderNumber) inner join customers c on orders.customerNumber = c.customerNumber)" + 
					"group by orders.orderNumber having sum(od.quantityOrdered*od.priceEach) > 25000;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			
			while (rs.next())  {
				String customerName = rs.getString("customerName");
				int orderNumber = rs.getInt("orderNumber");
				double totalValue = rs.getDouble("sum(quantityOrdered*priceEach)");
				
				b.append("\n" + "Customer Name: " + customerName);
				b.append("\n" + "Order Number: " + orderNumber +"\t" + "Amount: $" + df.format(totalValue));
				b.append("\n");
			}
			
		}
		catch (SQLException e) {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		assertEquals(b.toString(), ordersDAO.getOrdersOverTwentyFiveThousand());
		ordersDAO.closeConn();
		
	}
}
