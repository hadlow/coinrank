package com.coinrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class API
{
	//private String m_tickerURL = "https://api.coinmarketcap.com/v1/ticker/?limit=0";
	
	private String m_tickerURL= "https://api.coinmarketcap.com/v1/ticker/?limit=5";
	
	private String m_historyURL = "https://graphs.coinmarketcap.com/currencies/";
	
	private String m_newsURL = "https://min-api.cryptocompare.com/data/news/";
	
	private String m_globalURL = "https://api.coinmarketcap.com/v1/global/";
	
	private String m_tickerMoreURL = "https://min-api.cryptocompare.com/data/all/coinlist";
	
	private String m_coinInfoURL = "https://www.cryptocompare.com/api/data/coinsnapshotfullbyid/?id=";
	
	private String m_activeURL = "";
	
	private String Read(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while((cp = rd.read()) != -1)
			sb.append((char) cp);
		
		return sb.toString();
	}
	
	public API Ticker() throws IOException
	{
		this.m_activeURL = this.m_tickerURL;
		
		return this;
	}
	
	public API History(String cmc_id) throws IOException
	{
		this.m_activeURL = this.m_historyURL + cmc_id;
		
		return this;
	}
	
	public API News() throws IOException
	{
		this.m_activeURL = this.m_newsURL;
		
		return this;
	}
	
	public API Global() throws IOException
	{
		this.m_activeURL = this.m_globalURL;
		
		return this;
	}
	
	public API TickerMore() throws IOException
	{
		this.m_activeURL = this.m_tickerMoreURL;
		
		return this;
	}
	
	public API CoinInfo(String coin_id) throws IOException
	{
		this.m_activeURL = this.m_coinInfoURL + coin_id;
		
		return this;
	}
	
	public JsonObject Get() throws IOException
	{
		// Open stream with server
		InputStream is = new URL(this.m_activeURL).openStream();
		
		try
		{
			// Get contents from server
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			// Wrap JSON in data key
			String jsonText = "{\"data\":" + this.Read(rd) + "}";
			
			// Return the JSON object
			return new JsonParser().parse(jsonText).getAsJsonObject();
		} finally {
			is.close();
		}
	}
}