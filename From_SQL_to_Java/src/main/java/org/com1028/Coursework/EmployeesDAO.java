package org.com1028.Coursework;
/*
 * @author Rio Viernes-Gonzales
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeesDAO extends DBConnection {
	private List<Employees> allEmployees = null;
	private HashMap<String, Integer> bLink = null;
	
	public EmployeesDAO()  {
		super();
		this.allEmployees = new ArrayList<Employees>();
		this.bLink = new HashMap<String, Integer>();
	}
	
	/*
	 * if the city is 'Boston', then store 'Boston' as the key and the officeCode as the value
	 */
	public HashMap<String, Integer> boston()  {	 
		
		try  {
			String query1 = "SELECT * from offices";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			
			while(rs.next()) {
				int officeCode = rs.getInt("officeCode");
				String city = rs.getString("city");
				
				if (city.equals("Boston"))  {
					this.bLink.put(city, officeCode);
				}
			}
		}  catch (SQLException e)  {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
	return this.bLink;  
	}
	 
	/*
	 * if the officeCode stored with the 'Boston' key from the boston() method is the same as the employee's
	 * officeCode, then create an object and store the employees' details into an ArrayList. 
	 */
	public List<Employees> bEmployees()  {
		try {
			String q = "SELECT * from employees";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			
			while(rs.next())  {
				int employeeNumber = rs.getInt("employeeNumber");
				String lastName = rs. getString("lastName");
				String firstName = rs.getString("firstName");
				String extension = rs.getString("extension");
				String email = rs.getString("email");
				int officeCode = rs.getInt("officeCode");
				int reportsTo = rs.getInt("reportsTo");
				String jobTitle = rs.getString("jobTitle");
								
				if (officeCode == this.boston().get("Boston"))  { 
					this.allEmployees.add(new Employees(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle));
				}
			}
		}
		catch (SQLException e)  {
			System.out.println("Error while retrieving records.");
			throw new RuntimeException(e);
		}
		return this.allEmployees;
	}
	
	/*
	 * prints all the employees who work in the Boston
	 */
	public String displayBostonEmployees()  {
		StringBuffer b = new StringBuffer();
		b.append("\n" + "Who are the employees in Boston?" + "\n");
		
		for (Employees employee : this.bEmployees())  {
			b.append("\n" + "Employee Number: " + employee.getEmployeeNumber() + "\n");
			b.append("Name: " + employee.getLastName());
			b.append(", ");
			b.append(employee.getFirstName() + "\n");
			b.append("Extension: " + employee.getExtension() + "\n");
			b.append("Email: " + employee.getEmail() + "\n");
			b.append("Office Code: " + employee.getOfficeCode() + "\n");
			b.append("Reports To: " + employee.getReportsTo() + "\n");
			b.append("Job Title: " + employee.getJobTitle() + "\n");
		}
		return b.toString();
	}
}
