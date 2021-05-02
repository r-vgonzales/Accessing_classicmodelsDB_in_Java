package org.com1028.Coursework;
/*
 * @author Rio Viernes-Gonzales
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	protected Connection con;
	private static final String db = "jdbc:mysql://localhost:3306/classicmodels?serverTimezone=UTC";

	public DBConnection() { 
		super();
		this.con = null;
		this.openConnection();
	
	}

	public void openConnection()  {
		try  {
			if (this.con == null || this.con.isClosed())  {
				this.con = DriverManager.getConnection(db, "root", "");
			}
		}  catch (SQLException e)  {
			System.out.println("Error: Failed to create a connection to the database.");
			throw new RuntimeException(e);
		}
	}
	
	public void closeConn()  {
		try  {
			  DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			if (this.con != null)  {
				this.con.close();
			}
			System.out.println("Successfully closed the connection to the database.");
		}
		catch  (Exception e)  {
			System.out.println("Failed to close connection to database.");
			throw new RuntimeException(e);
		}
			
		}
}
