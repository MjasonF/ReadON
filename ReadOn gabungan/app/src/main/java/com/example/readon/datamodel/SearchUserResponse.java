package com.example.readon.datamodel;

import com.google.gson.annotations.SerializedName;

public class SearchUserResponse {
    private Boolean isFriend, isOnline;
    @SerializedName("id")
    private String userId;
    private String username;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
