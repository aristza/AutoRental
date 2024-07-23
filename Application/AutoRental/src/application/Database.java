package application;

import java.sql.*;

public class Database {

	private Connection conn;
	private static Database db;

	public static Database getInstance() {
		if (db == null) db = new Database();
		return db;
	}
	
	public void connect() {
		
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Auto Rental", "postgres", "97237445");
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet execute(String query, Boolean returns) {
		
		ResultSet result= null;
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			if (returns) result = statement.executeQuery();
			else statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
}
