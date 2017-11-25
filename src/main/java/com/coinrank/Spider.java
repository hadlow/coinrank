package com.coinrank;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Spider
{
	public Spider()
	{
		
	}
	
	public void crawl()
	{
		API api = new API();
		
		try
		{
			JsonArray ticker = api.Ticker().Get().getAsJsonArray("data");
			JsonObject tickerMore = api.TickerMore().Get().getAsJsonObject("data").getAsJsonObject("Data");
			
			for(JsonElement coin : ticker)
			{
				new Coin((JsonObject) coin, tickerMore);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}