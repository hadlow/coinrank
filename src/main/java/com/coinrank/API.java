package com.coinrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class API
{
	private String tickerURL = "https://api.coinmarketcap.com/v1/ticker/?limit=0";
	
	private String historyURL = "https://graphs.coinmarketcap.com/currencies/";
	
	private String newsURL = "https://min-api.cryptocompare.com/data/news/";
	
	private String globalURL = "https://api.coinmarketcap.com/v1/global/";
	
	private String read(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while ((cp = rd.read()) != -1)
			sb.append((char) cp);
		
		return sb.toString();
	}
	
	public JSONObject get(String url) throws IOException, JSONException
	{
		InputStream is = new URL(url).openStream();
		
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = this.read(rd);
			JSONObject json = new JSONObject(jsonText);
			
			return json;
		} finally {
			is.close();
		}
	}
}