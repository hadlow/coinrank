package com.coinrank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Spider
{
	private List<Coin> m_coins = new ArrayList<Coin>();
	
	private API m_api = new API();
	
	public Spider()
	{
		try
		{
			JsonArray ticker = this.m_api.Ticker().Get().getAsJsonArray("data");
			JsonObject tickerMore = this.m_api.TickerMore().Get().getAsJsonObject("data").getAsJsonObject("Data");
			
			for(JsonElement coin : ticker)
			{
				this.m_coins.add(new Coin((JsonObject) coin, tickerMore));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void crawl()
	{
		try
		{
			JsonArray ticker = this.m_api.Ticker().Get().getAsJsonArray("data");
			JsonObject tickerMore = this.m_api.TickerMore().Get().getAsJsonObject("data").getAsJsonObject("Data");
			
			for(JsonElement coin : ticker)
			{
				this.m_coins.add(new Coin((JsonObject) coin, tickerMore));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}