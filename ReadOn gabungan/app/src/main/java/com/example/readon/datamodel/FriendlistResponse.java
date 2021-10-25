package com.example.readon.datamodel;

import com.google.gson.annotations.SerializedName;

public class FriendlistResponse {
    @SerializedName("friend_user_id")
    private FriendlistItemResponse friend;

    @SerializedName("user_id")
    private FriendlistItemResponse user;

    public FriendlistItemResponse getFriend() {
        return friend;
    }

    public FriendlistItemResponse getUser() {
        return user;
    }
}

