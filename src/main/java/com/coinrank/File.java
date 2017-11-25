package com.coinrank;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class File
{
	private String m_fileName = "";
	
	public File(String file)
	{
		this.m_fileName = "src/main/resources/" + file;
	}
	
	public String ReadFull()
	{
		String line = null;
		String contents = "";
		
		try
		{
			FileReader fileReader = new FileReader(this.m_fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null)
				contents = contents + line;
			
		    bufferedReader.close();
		} catch(FileNotFoundException ex) {
		    System.out.println("Unable to open file '" + this.m_fileName + "'");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return contents;
	}
}