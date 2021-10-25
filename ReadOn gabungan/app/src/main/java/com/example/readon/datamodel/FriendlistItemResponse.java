package com.example.readon.datamodel;

import com.google.gson.annotations.SerializedName;

public class FriendlistItemResponse {
    @SerializedName("_id")
    private String userId;
    private String username, avatar;
    private Boolean isOnline;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public Boolean getOnline() {
        return isOnline;
    }
}
