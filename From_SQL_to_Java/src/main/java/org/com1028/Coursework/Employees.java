package org.com1028.Coursework;
/*
 * Rio Viernes-Gonzales
 */
public class Employees {
	// fields
	private int employeeNumber = 0;
	private String lastName = null;
	private String firstName = null;
	private String extension = null; //x101
	private String email = null;
	private int officeCode = 0;
	private int reportsTo = 0;
	private String jobTitle = null;
	
	public Employees(int employeeNumber, String lastName, String firstName, String extension, String email,
			int officeCode, int reportsTo, String jobTitle) {
		super();
		this.employeeNumber = employeeNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.extension = extension;
		this.email = email;
		this.officeCode = officeCode;
		this.reportsTo = reportsTo;
		this.jobTitle = jobTitle;
	}
	
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getExtension() {
		return extension;
	}
	public String getEmail() {
		return email;
	}
	public int getOfficeCode() {
		return officeCode;
	}
	public int getReportsTo() {
		return reportsTo;
	}
	public String getJobTitle() {
		return jobTitle;
	}
}
