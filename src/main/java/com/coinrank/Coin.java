package com.coinrank;

import java.sql.SQLException;

public class Coin
{
	private String coin_id;
	
	public Coin(String coin_id)
	{
		this.coin_id = coin_id;
	}
	
	public void SaveChart()
	{
		
	}
	
	public void SaveHistory()
	{
		
	}
	
	private void CreateHistoryTable(int coin_id)
	{
		File file = new File("create_history_table.sql");
		String query = file.ReadFull().replace("{coin_id}", Integer.toString(coin_id));
		
		try
		{
			DB db = DB.Connect();
			
			db.Update(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}