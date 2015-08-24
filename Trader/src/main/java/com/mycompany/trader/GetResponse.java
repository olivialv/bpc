/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Olivia
 */
public class GetResponse {

    private JSONObject selectedUser = null;
    private JSONObject selectedUserData = null;

    public String getId(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String userId = selectedUserData.getString("id");
        return userId;
    }

    public String getFirstname(JSONArray jsonArr, int selectedRowIndex) {

        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String firstname = selectedUserData.getString("firstName");
        return firstname;
    }

    public String getLastname(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String lastname = selectedUserData.getString("lastName");
        return lastname;
    }

    public String getEmail(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String email = selectedUserData.getString("emailAddress");
        return email;
    }

    public boolean getVerified(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        boolean verified = selectedUserData.getBoolean("verified");
        return verified;
    }

    public String getResponsibleUserId(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        if (selectedUserData.isNull("responsibleUserId")) {
            return null;

        } else {
            String responsibleUserId = selectedUserData.getString("responsibleUserId");
            return responsibleUserId;
        }
    }

    public String getAccountId(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String AccountId = selectedUserData.getString("accountId");
        return AccountId;
    }

    public String getKeyName(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String keyName = selectedUserData.getString("name");
        return keyName;
    }

    public String getAddress(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String address = selectedUserData.getString("address");
        return address;
    }

    public String getPhone(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        selectedUserData = (JSONObject) selectedUser.get("externalUser");
        String phone = selectedUserData.getString("phoneNumber");
        return phone;
    }

    public String getStrategy(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        String strategy = selectedUser.getString("type");
        return strategy;
    }

    public String getStatus(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        String status = selectedUser.getString("status");
        return status;
    }

    public double getAccountValue(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        double accountValue = selectedUser.getDouble("accountValue");
        return accountValue;
    }

    public String getMaxDaily(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        String maxDaily = selectedUser.getString("maxDailyTradeVolume");
        return maxDaily;
    }

    public int getMaxSingle(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        int maxSingle = selectedUser.getInt("maxSingleTradeVolume");
        return maxSingle;
    }

    public int getNetOpenPos(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        int maxNetOpenPos = selectedUser.getInt("maxNetOpenPosition");
        return maxNetOpenPos;
    }

    public String getRole(JSONArray jsonArr, int selectedRowIndex) {
        selectedUser = jsonArr.getJSONObject(selectedRowIndex);
        String role = selectedUser.getString("role");
        return role;
    }
    
    

}
