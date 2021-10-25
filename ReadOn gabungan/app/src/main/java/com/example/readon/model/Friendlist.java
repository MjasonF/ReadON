package com.example.readon.model;

public class Friendlist {
    private String userId, frienduserId, friendlistId;
    private Boolean isAccepted;

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public String getFriendlistId() {
        return friendlistId;
    }

    public void setFriendlistId(String friendlistId) {
        this.friendlistId = friendlistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrienduserId() {
        return frienduserId;
    }

    public void setFrienduserId(String frienduserId) {
        this.frienduserId = frienduserId;
    }
}
