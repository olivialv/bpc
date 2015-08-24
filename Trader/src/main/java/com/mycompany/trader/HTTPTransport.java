/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Olivia
 */
public class HTTPTransport {
    
    private static String url = "http://104.155.25.165:8080/services/bpc/";
    
    public static HttpPost createPost(String resource, String data){
        try {
            HttpPost post  = new HttpPost(url + resource); 
            StringEntity input = new StringEntity(data);
            input.setContentType("application/json");
            post.setEntity(input);
            return post;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static HttpGet createGet(String resource, String userId, String hashedSig, String timeStamp, String nonce){
        try {
            HttpGet get  = new HttpGet(url + resource); 
            get.setHeader("Content-Type", "application/json");
            get.addHeader("Authorization", userId+":"+hashedSig);
            get.addHeader("x-timestamp", timeStamp);
            get.addHeader("nonce",nonce);
            return get;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static String sendData(HttpPost data, String header) throws Exception {
       try {
            
		DefaultHttpClient httpClient = new DefaultHttpClient();
 
		HttpResponse response = httpClient.execute(data);
                StringBuilder buffer = new StringBuilder();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));
 
		String output;
		while ((output = br.readLine()) != null) {
			buffer.append(output);
		}
                System.out.println("response from server:");
                System.out.println(buffer.toString());
		if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                    buffer.append("error_code:" + response.getStatusLine().getStatusCode());
                }
		httpClient.getConnectionManager().shutdown();
 
                return buffer.toString();
                
	  } catch (Exception e) {
		//e.printStackTrace();
                throw new Exception("server returned code: " +e.getMessage());
	  }
 
    }
    
    
    public static String sendGetReq(HttpGet data) {
       try {
            
		DefaultHttpClient httpClient = new DefaultHttpClient();
 
		HttpResponse response = httpClient.execute(data);
                StringBuilder buffer = new StringBuilder();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));
 
		String output;
		while ((output = br.readLine()) != null) {
			buffer.append(output);
		}
                System.out.println("response from server:");
                System.out.println(buffer.toString());
		if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
			throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
 
 
		httpClient.getConnectionManager().shutdown();
		return buffer.toString();
 
	  } catch (Exception e) {
                return null;
	  }
 
    }
    
    
}
