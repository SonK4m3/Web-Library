package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	protected static Connection con = null;
	private String dbUrl = "jdbc:mysql://localhost:3306/library";
	private String dbUsername = "root";
	private String dbPassword = "123456";
	
	public DAO() {
		
	}
	
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	protected void connectToMySQL() {
		try {
			if(DAO.con == null || DAO.con.isClosed()) con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
