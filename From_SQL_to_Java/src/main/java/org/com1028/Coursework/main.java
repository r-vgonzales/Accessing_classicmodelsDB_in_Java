package org.com1028.Coursework;
/*
 * @author Rio Viernes-Gonzales
 */
public class main extends DBConnection{
	
	public static void main(String[] args) {
		
		PaymentsDAO paymentsDAO = null;
		EmployeesDAO employeesDAO =  null;
		OrdersDAO ordersDAO = null;
	
		try  {
			paymentsDAO = DAOFactory.getPaymentsDAO();
			employeesDAO = DAOFactory.getEmployeesDAO();
			ordersDAO = DAOFactory.getOrdersDAO();
			
			
			paymentsDAO.openConnection();
			employeesDAO.openConnection();
			ordersDAO.openConnection();
			
			/*
			 * for first requirement
			 */
			paymentsDAO.tAverage(); // calls this Average function - performs the calculation 
			System.out.println(paymentsDAO.displayBigPayments()); 
			
			/*
			 * for second requirement
			 */
			System.out.println(employeesDAO.displayBostonEmployees());
			
			/*
			 * for third requirement
			 */
			System.out.println(ordersDAO.getOrdersOverTwentyFiveThousand());

		}
		catch (Exception e)  {
			e.printStackTrace();
		}
		finally {
			if (paymentsDAO != null && employeesDAO != null && ordersDAO != null)  {
				paymentsDAO.closeConn();
				employeesDAO.closeConn();
				ordersDAO.closeConn();
				
				System.out.println(("Closed all connections to the database"));
			}
			else  {
				throw new NullPointerException();
			}
		}
	}
}
