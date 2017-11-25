package com.coinrank;

import java.io.ByteArrayInputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3
{
	private AmazonS3 m_client;
	
	private String m_bucket = "coinrank";
	
	public S3()
	{
		AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
		
		this.m_client = new AmazonS3Client(credentials);
	}
	
	public void Save(String filename, String contents)
	{
		try
		{
			// Get the content string as a byte array
			byte[] contentAsBytes = contents.getBytes("UTF-8");
			
			// Load the byte array into an input stream
			ByteArrayInputStream contentsAsStream = new ByteArrayInputStream(contentAsBytes);
			
			// Create a metadata instance
			ObjectMetadata md = new ObjectMetadata();
			
			// Get the content length and save into the metadata
			md.setContentLength(contentAsBytes.length);
			
			// Upload the file to AWS S3
			this.m_client.putObject(new PutObjectRequest(this.m_bucket, filename, contentsAsStream, md));
		} catch(AmazonServiceException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}