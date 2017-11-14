package com.coinrank;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class Coinrank {

	public static void main(String[] args)
	{
		API api = new API();
		DB db = DB.Connect();
		
		db.CreateHistoryTable(3);
		
		try {
			JSONObject history = api.get("https://graphs.coinmarketcap.com/currencies/bitcoin/");
			//System.out.println(history.get("market_cap_by_available_supply"));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Coinrank finished.");
	}
}