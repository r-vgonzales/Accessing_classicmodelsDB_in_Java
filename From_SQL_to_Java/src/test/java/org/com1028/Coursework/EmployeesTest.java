package org.com1028.Coursework;
/*
 * @author Rio Viernes-Gonzales
 */
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.com1028.Coursework.DBConnection; 
import org.junit.Test;
  
  public class EmployeesTest extends DBConnection {
  
  
  /*
   * selects 'Boston' and its officeCode from the offices table
   */
  
  public HashMap<String, Integer> getCorrectBoston() { 
	  HashMap<String, Integer> correctbOfficeCode = new HashMap<String, Integer>(); 
	  
	  try  {
		  String q = "SELECT officeCode, city from offices where city = 'Boston'";
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery(q);
		  
		  while(rs.next())  {
			  int officeCode = rs.getInt("officeCode");
			  String city = rs.getString("city");
			  correctbOfficeCode.put(city, officeCode);
		  }
	  }
	  catch (SQLException e)  {
		  System.out.println("Error while retrieivng records.");
		  throw new RuntimeException(e);
	  }
  return correctbOfficeCode;
  } 
  
  @Test
  public void testBostonCode()  {
	  EmployeesDAO employeesDAO = new EmployeesDAO();
	  employeesDAO.openConnection();
	  assertEquals(employeesDAO.boston(), this.getCorrectBoston());
	  employeesDAO.closeConn();
  }
  
  public List<Employees> getCorrectBEmployees(){
	  List<Employees>  correctBEmployees = new ArrayList<Employees>();
	  try  {
		  String q = "select employeeNumber, lastName, firstName, extension, email, employees.officeCode, reportsTo, jobTitle from employees right join offices on employees.officeCode=offices.officeCode where offices.city='Boston'"; 
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery(q);
		  
		  while (rs.next())  {
			int employeeNumber = rs.getInt("employeeNumber");
			String lastName = rs. getString("lastName");
			String firstName = rs.getString("firstName");
			String extension = rs.getString("extension");
			String email = rs.getString("email");
			int officeCode = rs.getInt("officeCode");
			int reportsTo = rs.getInt("reportsTo");
			String jobTitle = rs.getString("jobTitle");
				
				correctBEmployees.add(new Employees(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle));
		  }
  }
	  catch (SQLException e)  {
		  System.out.println("Error in retrieving records.");
		  throw new RuntimeException(e);
	  }
	  return correctBEmployees;
  }
  
  @Test
  public void testGetBostonEmployees()  {
	 EmployeesDAO employeesDAO = new EmployeesDAO();
	 employeesDAO.openConnection();
	 List<Employees> testBEmployees = employeesDAO.bEmployees();
	 
	 for (int i = 0; i < this.getCorrectBEmployees().size(); i++)  {
		 assertEquals(testBEmployees.get(i).getEmployeeNumber(), this.getCorrectBEmployees().get(i).getEmployeeNumber());
		 assertEquals(testBEmployees.get(i).getLastName(), this.getCorrectBEmployees().get(i).getLastName());
		 assertEquals(testBEmployees.get(i).getFirstName(), this.getCorrectBEmployees().get(i).getFirstName());
		 assertEquals(testBEmployees.get(i).getExtension(), this.getCorrectBEmployees().get(i).getExtension());
		 assertEquals(testBEmployees.get(i).getEmail(), this.getCorrectBEmployees().get(i).getEmail());
		 assertEquals(testBEmployees.get(i).getOfficeCode(), this.getCorrectBEmployees().get(i).getOfficeCode());
		 assertEquals(testBEmployees.get(i).getReportsTo(), this.getCorrectBEmployees().get(i).getReportsTo());
		 assertEquals(testBEmployees.get(i).getJobTitle(), this.getCorrectBEmployees().get(i).getJobTitle());
		 
		 employeesDAO.closeConn();
	 }
  }
	 
	 @Test
	 public void testDisplayBostonEmployees()  {
		EmployeesDAO employeesDAO = new EmployeesDAO();
		StringBuffer b = new StringBuffer();
		 
		b.append("\n" + "Who are the employees in Boston?" + "\n");
		
		for (Employees employees :  this.getCorrectBEmployees())  {
			b.append("\n" + "Employee Number: " + employees.getEmployeeNumber() + "\n");
			b.append("Name: " + employees.getLastName());
			b.append(", ");
			b.append(employees.getFirstName() + "\n");
			b.append("Extension: " + employees.getExtension() + "\n");
			b.append("Email: " + employees.getEmail() + "\n");
			b.append("Office Code: " + employees.getOfficeCode() + "\n");
			b.append("Reports To: " + employees.getReportsTo() + "\n");
			b.append("Job Title: " + employees.getJobTitle() + "\n");
		}
		
		employeesDAO.openConnection();
		assertEquals(b.toString(), employeesDAO.displayBostonEmployees());
		employeesDAO.closeConn();
	 }
 }
 