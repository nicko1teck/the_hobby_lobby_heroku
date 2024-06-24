package com.o1teck.service;
import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.http.xml.*;

@Service
public class HttpClientService {
	
	//just a flag for later
	private boolean postSent;
	
	//Will likely not use, JTH
	private String mostRecentResponse;
	
	

	//No-Arg Constructor
	public HttpClientService() {	
	}
	
	
	
	// GET/SET
	public boolean isPostSent() {
		return postSent;
	}
	public void setPostSent(boolean postSent) {
		this.postSent = postSent;
	}
	

	
	
	public HttpRequest createPostRequest(List<NameValuePair> body, String urlToPostTo) {
		
		//Get a RequestFactory
		HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
		
		//Make a Byte Array of the XML String (here dataToPost input variable)
       // byte[] xmlData = xmlString.getBytes();
		
		//Get some HttpContent (WTH is that?)
	    //HttpContent httpContent = new ByteArrayContent("application/xml", xmlData);  //new byte[]{});
	
		//	Build the post request
		//HttpRequest postRequest = requestFactory.buildPostRequest(new GenericUrl(urlToPostTo), httpContent);
		 
		return null;  
	}


	//Send POST request to the API
	public String sendPostRequest(String xmlString, String urlToPostTo) throws IOException {
		
		
        //Set the URL to which to POST  (WILL GET THIS FROM PROPS FILE EVENTUALLY)
	    //String urlToPostTo = "https://api.cloudinary.com/v1_1/nicko1teck/image/upload";
	
	    //create an indicator variable 
	    String rawResponse = null;
	    
	    //Execute and store response
	    //rawResponse = requestPost.execute().parseAsString();
	    
	    return rawResponse;
	    
	}

	
		
}