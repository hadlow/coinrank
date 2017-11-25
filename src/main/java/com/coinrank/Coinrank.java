package com.coinrank;

public class Coinrank
{
	public static void main(String[] args)
	{
		Spider spider = new Spider();
		
		spider.crawl();
		
		System.out.println("Coinrank finished.");
	}
}