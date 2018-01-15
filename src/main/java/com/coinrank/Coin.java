package com.coinrank;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Coin
{
	private String m_symbol;
	
	private String m_cmc_id;
	
	private String m_name;
	
	private String m_algorithm;
	
	private String m_proof;
	
	private String m_premined;
	
	private String m_max_coins;
	
	private String m_website;
	
	private String m_twitter;
	
	private String m_ico_status;
	
	private String m_whitepaper;
	
	private DB m_db;
	
	private API m_api;
	
	private Map<String, String> m_proofs;
	
	private Map<String, String> m_symbols;
	
	public Coin(JsonObject coinData, JsonObject coinMoreList)
	{
		this.m_db = DB.Connect();
		this.m_api = new API();
		
		this.LoadProofs();
		this.LoadSymbols();
		this.Initialize(coinData, coinMoreList);
	}
	
	private void LoadProofs()
	{
		this.m_proofs = new HashMap<String, String>();
		
		this.m_proofs.put("PoW", "PoW");
		this.m_proofs.put("Pow", "PoW");
		this.m_proofs.put("dPow", "PoW");
		this.m_proofs.put("PoW/", "PoW");
		this.m_proofs.put("PoS", "PoS");
		this.m_proofs.put("PoS/", "PoS");
		this.m_proofs.put("PoSv", "PoS");
		this.m_proofs.put("DPoS", "PoS");
		this.m_proofs.put("LPoS", "PoS");
		this.m_proofs.put("PoW/PoS", "PoW/PoS");
	}
	
	private void LoadSymbols()
	{
		this.m_symbols = new HashMap<String, String>();
		
		this.m_symbols.put("MIOTA", "IOT");
		this.m_symbols.put("BCC", "BCCOIN");
	}
	
	private void Initialize(JsonObject coinData, JsonObject coinMoreList)
	{
		this.ParseData(coinData, coinMoreList);
		
		try
		{
			ResultSet coin = this.m_db.Query("SELECT * FROM coins WHERE symbol='" + this.m_symbol + "' LIMIT 1");
			
			if(coin.next())
			{
				
			} else {
				this.AddToCoins();
				this.CreateHistoryTable();
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
	
	private void UpdateHistory()
	{
		try
		{
			this.m_db.Insert("history_" + this.m_symbol, "time, price_usd, price_btc, market_cap, volume_usd", "NOW(), '" + "PRICE" + "', '', '', ''");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void ParseData(JsonObject coinData, JsonObject coinMoreList)
	{
		JsonObject coinMore = null;
		JsonObject coinInfo = null;
		String symbolAPI = null;
		
		// Get more details from coinMoreList
		for(Map.Entry<String, JsonElement> entry : coinMoreList.entrySet())
		{
			symbolAPI = this.m_symbols.get(coinData.get("symbol").getAsString());
			
			if(symbolAPI == null)
				symbolAPI = coinData.get("symbol").getAsString();
			
			if(symbolAPI.equals(entry.getValue().getAsJsonObject().getAsJsonPrimitive("Symbol").getAsString()))
				coinMore = entry.getValue().getAsJsonObject();
		}
		
		// Get info from API coinInfo
		try
		{
			coinInfo = this.m_api.CoinInfo(coinMore.getAsJsonPrimitive("Id").getAsString()).Get().getAsJsonObject("data").getAsJsonObject("Data");
		} catch (NullPointerException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.m_symbol = coinData.get("symbol").getAsString();
		this.m_cmc_id = coinData.get("id").getAsString();
		this.m_name = coinData.get("name").getAsString();
		this.m_algorithm = coinMore.getAsJsonPrimitive("Algorithm").getAsString();
		this.m_proof = this.m_proofs.get(coinMore.getAsJsonPrimitive("ProofType").getAsString());
		this.m_premined = coinMore.getAsJsonPrimitive("FullyPremined").getAsString();
		this.m_max_coins = coinMore.getAsJsonPrimitive("TotalCoinSupply").getAsString();
		this.m_website = coinInfo.getAsJsonObject("General").getAsJsonPrimitive("AffiliateUrl").getAsString();
		this.m_twitter = coinInfo.getAsJsonObject("General").getAsJsonPrimitive("Twitter").getAsString().replace("@", "");
		this.m_ico_status = coinInfo.getAsJsonObject("ICO").getAsJsonPrimitive("Status").getAsString();
		this.m_whitepaper = Jsoup.parse(coinInfo.getAsJsonObject("ICO").getAsJsonPrimitive("WhitePaper").getAsString()).body().text();
		
		if(this.m_proof == null)
			this.m_proof = coinMore.getAsJsonPrimitive("ProofType").getAsString();
		
		if(this.m_max_coins.equals("N/A"))
			this.m_max_coins = "0";
	}
	
	private void AddToCoins()
	{
		try
		{
			this.m_db.Insert("coins", "symbol, cmc_id, name, algorithm, proof, premined, max_coins, website, twitter, reddit, explorer, github, ico_status, whitepaper", "'" + this.m_symbol + "', '" + this.m_cmc_id + "', '" + this.m_name + "', '" + this.m_algorithm + "', '" + this.m_proof + "', '" + this.m_premined + "', '" + this.m_max_coins + "', '" + this.m_website + "', '" + this.m_twitter + "', '', '', '', '" + this.m_ico_status + "', '" + this.m_whitepaper + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void CreateHistoryTable()
	{
		File file = new File("create_history_table.sql");
		String query = file.ReadFull().replace("{coin_id}", this.m_symbol);
		
		try
		{
			this.m_db.Update(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}