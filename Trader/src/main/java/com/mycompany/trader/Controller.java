/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trader;

import javax.swing.JDialog;
import javax.swing.JFrame;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

/**
 *
 * @author Olivia
 */
public class Controller {
    private String userId;

    private String sessionToken;
    
    private String accountId;

    

    //private ArrayList<User> listOfUsers = new ArrayList<User>();

    HTTPTransport httpTransport = new HTTPTransport();

    JFrame adminUi = null;

    JDialog addAccDialog = null;

    public void setUi(JFrame ui) {
        this.adminUi = ui;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setDialog(JDialog dialog) {
        this.addAccDialog = dialog;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getAccountId() {
        return accountId;
    }
    
    public void serSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    public String getAllTrade (String userId) {
//        userId = "861fb857-d8ea-4927-94e7-ce8d81e3a5cf";
        
        String getAllUsersResource = "account/getAllTrades/" + userId;

        RequestSigner singer = new RequestSigner();
        String timeStamp = singer.getTimestamp();
        String nonce = singer.getNextRequestNonce();
        String httpmethod = "GET";
        String hashedSig = singer.getSignature(sessionToken, getAllUsersResource, httpmethod, timeStamp, nonce);

        HttpGet get = HTTPTransport.createGet(getAllUsersResource, this.userId, hashedSig, timeStamp, nonce);

        //if successful, the users will be returned into this String (use to populate table, or create JSON objects)
        return HTTPTransport.sendGetReq(get);
    }
    
    public String getDailyClosedTrade (String userId) {
//        userId = "861fb857-d8ea-4927-94e7-ce8d81e3a5cf";
        
        String getAllUsersResource = "account/todaysClosedTrades/" + userId;

        RequestSigner singer = new RequestSigner();
        String timeStamp = singer.getTimestamp();
        String nonce = singer.getNextRequestNonce();
        String httpmethod = "GET";
        String hashedSig = singer.getSignature(sessionToken, getAllUsersResource, httpmethod, timeStamp, nonce);

        HttpGet get = HTTPTransport.createGet(getAllUsersResource, this.userId, hashedSig, timeStamp, nonce);

        //if successful, the users will be returned into this String (use to populate table, or create JSON objects)
        return HTTPTransport.sendGetReq(get);
    }
    
    public String getUser(String userId) {
        //GET Request to get a list of all users ( currently returned as a String
        String getAllUsersResource = "user/getUserAccount/" + userId;

        RequestSigner singer = new RequestSigner();
        String timeStamp = singer.getTimestamp();
        String nonce = singer.getNextRequestNonce();
        String httpmethod = "GET";
        String hashedSig = singer.getSignature(sessionToken, getAllUsersResource, httpmethod, timeStamp, nonce);

        HttpGet get = HTTPTransport.createGet(getAllUsersResource, this.userId, hashedSig, timeStamp, nonce);

        //if successful, the users will be returned into this String (use to populate table, or create JSON objects)
        return HTTPTransport.sendGetReq(get);
    }
    
    
    public String updateUser(String data, String userId) {
        String resource = "user/" + userId;
        HttpPost post = httpTransport.createPost(resource, data);
        RequestSigner singer = new RequestSigner();
        String timeStamp = singer.getTimestamp();
        String nonce = singer.getNextRequestNonce();
        String httpmethod = "POST";
        String hashedSig = singer.getSignature(sessionToken, resource, httpmethod, timeStamp, nonce);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", " " + this.userId + ":" + hashedSig);
        post.addHeader("x-timestamp", timeStamp);
        post.addHeader("nonce", nonce);
        String updateId = null;
        try {
            String updateBuffer = httpTransport.sendData(post, data);
            JSONObject updateObj = new JSONObject(updateBuffer);
            updateId = updateObj.getString("id");//change

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateId;
    }
    
    

}
