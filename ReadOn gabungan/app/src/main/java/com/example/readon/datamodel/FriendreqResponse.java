package com.example.readon.datamodel;

import com.google.gson.annotations.SerializedName;

public class FriendreqResponse {
    @SerializedName("isAccepted")
    private Boolean isAccepted;
    @SerializedName("_id")
    private String friendlistId;
    @SerializedName("user_id")
    private FriendreqItemResponse userId;
    @SerializedName("friend_user_id")
    private String friendUserId;

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getId() {
        return friendlistId;
    }

    public void setId(String id) {
        this.friendlistId = id;
    }

    public FriendreqItemResponse getUserId() {
        return userId;
    }

    public void setUserId(FriendreqItemResponse userId) {
        this.userId = userId;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

}
