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
	private String tickerURL = "https://api.coinmarketcap.com/v1/ticker/?limit=0";
	
	private String tickerURLtemp = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
	
	private String historyURL = "https://graphs.coinmarketcap.com/currencies/";
	
	private String newsURL = "https://min-api.cryptocompare.com/data/news/";
	
	private String globalURL = "https://api.coinmarketcap.com/v1/global/";
	
	private String read(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while((cp = rd.read()) != -1)
			sb.append((char) cp);
		
		return sb.toString();
	}
	
	public JsonObject getTicker() throws IOException
	{
		return this.get(this.tickerURLtemp);
	}
	
	public JsonObject getHistory() throws IOException
	{
		return this.get(this.historyURL);
	}
	
	public JsonObject getNews() throws IOException
	{
		return this.get(this.newsURL);
	}
	
	public JsonObject getGlobal() throws IOException
	{
		return this.get(this.globalURL);
	}
	
	public JsonObject get(String url) throws IOException
	{
		// Open stream with server
		InputStream is = new URL(url).openStream();
		
		try
		{
			// Get contents from server
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			// Wrap JSON in data key
			String jsonText = "{\"data\":" + this.read(rd) + "}";
			
			// Return the JSON object
			return new JsonParser().parse(jsonText).getAsJsonObject();
		} finally {
			is.close();
		}
	}
}