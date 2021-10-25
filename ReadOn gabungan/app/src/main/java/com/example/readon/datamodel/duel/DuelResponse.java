package com.example.readon.datamodel.duel;

import com.example.readon.datamodel.UserItemResponse;
import com.google.gson.annotations.SerializedName;

public class DuelResponse {

    @SerializedName("_id")
    private String duelId;

    @SerializedName("user_id")
    private UserItemResponse user;

    @SerializedName("opponent_id")
    private UserItemResponse opponent;

    private Boolean isUserFinished;
    private Boolean isOpponentFinished;
    private Boolean isDuelFinished;

    private String winner;

    private Integer point;

    @SerializedName("opponent_point")
    private Integer opponentPoint;

    private String type;

    @SerializedName("created_at")
    private String createdAt;

    public String getDuelId() {
        return duelId;
    }

    public UserItemResponse getUser() {
        return user;
    }

    public UserItemResponse getOpponent() {
        return opponent;
    }

    public Boolean getUserFinished() {
        return isUserFinished;
    }

    public Boolean getOpponentFinished() {
        return isOpponentFinished;
    }

    public Boolean getDuelFinished() {
        return isDuelFinished;
    }

    public Integer getPoint() {
        return point;
    }

    public Integer getOpponentPoint() {
        return opponentPoint;
    }

    public String getType() {
        return type;
    }

    public String getWinner() {
        return winner;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
