package org.com1028.Coursework;
/*
 * @author Rio Viernes-Gonzales
 */

public class DAOFactory {
	
	private static final PaymentsDAO Payments_DAO = new PaymentsDAO();
	private static final EmployeesDAO Employees_DAO = new EmployeesDAO();
	private static final OrdersDAO Orders_DAO = new OrdersDAO();
	
	private DAOFactory()  {
		
	}
	
	public static PaymentsDAO getPaymentsDAO()  {
		return DAOFactory.Payments_DAO;
	}
	
	public static EmployeesDAO getEmployeesDAO()  {
		return DAOFactory.Employees_DAO;
	}
	
	public static OrdersDAO getOrdersDAO()  {
		return DAOFactory.Orders_DAO;
	}

}
