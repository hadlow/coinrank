package com.coinrank;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Coinrank
{
	public static void main(String[] args)
	{
		API api = new API();
		DB db = DB.Connect();
		
		try
		{
			JsonArray ticker = api.getTicker().getAsJsonArray("data");
			
			for(JsonElement coin : ticker)
			{
				System.out.println(((JsonObject) coin).get("name").getAsString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Coinrank finished.");
	}
}