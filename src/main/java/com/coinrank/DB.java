package com.coinrank;

import java.sql.*;

public class DB
{
	public Connection m_connection;
	
	private Statement m_statement;
	
	private String m_url = "jdbc:mysql://localhost:3306/";
	
	private String m_database = "coinrank?allowMultiQueries=true";
	
	private String m_driver = "com.mysql.cj.jdbc.Driver";
	
	private String m_username = "root";
	
	private String m_password = "";
	
	public static DB m_db;
	
	private DB()
	{
		try
		{
			Class.forName(this.m_driver).newInstance();
			
			this.m_connection = (Connection)DriverManager.getConnection(this.m_url + this.m_database, this.m_username, this.m_password);
		} catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static synchronized DB Connect()
	{
		if(m_db == null)
			m_db = new DB();
		
		return m_db;
	}
	
	public ResultSet Select(String columns, String table, String attributes) throws SQLException
	{
		return this.Query("SELECT " + columns + " FROM " + table + " " + attributes);
	}
	
	public int Insert(String table, String columns, String values) throws SQLException
	{
		return this.Update("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")");
	}
	
	public ResultSet Query(String query) throws SQLException
	{
		this.m_statement = this.m_db.m_connection.createStatement();
		
		return this.m_statement.executeQuery(query);
	}
	
	public int Update(String query) throws SQLException
	{
		this.m_statement = this.m_db.m_connection.createStatement();
		
		return this.m_statement.executeUpdate(query);
	}
}