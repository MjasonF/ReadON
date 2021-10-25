package com.example.readon.datamodel;

import com.google.gson.annotations.SerializedName;

public class UserItemResponse {

    @SerializedName("_id")
    private String userId;
    private String username;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
