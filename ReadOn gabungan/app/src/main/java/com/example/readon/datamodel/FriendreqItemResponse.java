package com.example.readon.datamodel;

import com.google.gson.annotations.SerializedName;

public class FriendreqItemResponse {
    @SerializedName("isOnline")
    private Boolean isOnline;
    @SerializedName("_id")
    private String userId;
    @SerializedName("username")
    private String username;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
