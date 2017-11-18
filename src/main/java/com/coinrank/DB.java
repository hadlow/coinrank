package com.coinrank;

import java.sql.*;

public class DB
{
	public Connection conn;
	
	private Statement statement;
	
	private String url = "jdbc:mysql://localhost:3306/";
	
	private String database = "coinrank?allowMultiQueries=true";
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private String username = "root";
	
	private String password = "";
	
	public static DB db;
	
	private DB()
	{
		try
		{
			Class.forName(driver).newInstance();
			
			this.conn = (Connection)DriverManager.getConnection(this.url + this.database, this.username, this.password);
		} catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static synchronized DB Connect()
	{
		if(db == null)
			db = new DB();
		
		return db;
	}
	
	public ResultSet Query(String query) throws SQLException
	{
		statement = db.conn.createStatement();
		ResultSet res = statement.executeQuery(query);
		
		return res;
	}
	
	public int Update(String query) throws SQLException
	{
		statement = db.conn.createStatement();
		int result = statement.executeUpdate(query);
		
		return result;
	}
}