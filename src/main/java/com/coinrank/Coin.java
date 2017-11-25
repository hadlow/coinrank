package com.coinrank;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Coin
{
	private JsonObject m_coinData;
	
	private JsonObject m_coinMoreList;
	
	private DB m_db;
	
	private API m_api;
	
	public Coin(JsonObject coinData, JsonObject coinMoreList)
	{
		this.m_coinData = coinData;
		this.m_coinMoreList = coinMoreList;
		this.m_db = DB.Connect();
		this.m_api = new API();
		
		this.Initialize();
	}
	
	private void Initialize()
	{
		String symbol = this.m_coinData.get("symbol").getAsString();
		
		try
		{
			ResultSet coin = this.m_db.Query("SELECT * FROM coins WHERE symbol='" + symbol + "' LIMIT 1");
			
			if(coin.next())
			{
				System.out.println(coin.getString("symbol"));
			} else {
				this.AddToCoins();
				//this.CreateHistoryTable();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void SaveChart()
	{
		
	}
	
	private void SaveHistory()
	{
		
	}
	
	private void AddToCoins()
	{
		JsonObject coinMore = null;
		JsonObject coinInfo = null;
		
		// Get more details from coinMoreList
		for(Map.Entry<String, JsonElement> entry : this.m_coinMoreList.entrySet())
		{
			if((this.m_coinData.get("symbol").getAsString()).equals((entry.getValue().getAsJsonObject().getAsJsonPrimitive("Symbol").getAsString())))
			{
				coinMore = entry.getValue().getAsJsonObject();
			}
		}
		
		// Get info from API coinInfo
		try
		{
			coinInfo = this.m_api.CoinInfo(coinMore.getAsJsonPrimitive("Id").getAsString()).Get().getAsJsonObject("data").getAsJsonObject("Data");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String symbol = this.m_coinData.get("symbol").getAsString();
		String cmc_id = this.m_coinData.get("id").getAsString();
		String name = this.m_coinData.get("name").getAsString();
		String algorithm = coinMore.getAsJsonPrimitive("Algorithm").getAsString();
		String proof = coinMore.getAsJsonPrimitive("ProofType").getAsString();
		String premined = coinMore.getAsJsonPrimitive("FullyPremined").getAsString();
		String max_coins = coinMore.getAsJsonPrimitive("TotalCoinSupply").getAsString();
		String website = coinInfo.getAsJsonObject("General").getAsJsonPrimitive("AffiliateUrl").getAsString();
		String twitter = coinInfo.getAsJsonObject("General").getAsJsonPrimitive("Twitter").getAsString().replace("@", "");
		String ico_status = coinInfo.getAsJsonObject("ICO").getAsJsonPrimitive("Status").getAsString();
		String whitepaper = Jsoup.parse(coinInfo.getAsJsonObject("ICO").getAsJsonPrimitive("WhitePaper").getAsString()).body().text();
		
		/*
		try
		{
			this.m_db.Insert("coins", "'symbol', 'cmc_id', 'name', 'algorithm', 'proof', 'premined', 'max_coins', 'website', 'twitter', 'reddit', 'explorer', 'github', 'ico_status', 'whitepaper'", "values");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	}
	
	private void CreateHistoryTable()
	{
		File file = new File("create_history_table.sql");
		String query = file.ReadFull().replace("{coin_id}", this.m_coinData.get("id").getAsString());
		
		try
		{
			this.m_db.Update(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}