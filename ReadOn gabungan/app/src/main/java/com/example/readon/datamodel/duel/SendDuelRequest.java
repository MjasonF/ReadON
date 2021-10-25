package com.example.readon.datamodel.duel;

public class SendDuelRequest {

    private String userId;
    private String opponentId;
    private String type;
    private String duelId;
    private Integer point;

    public void setDuelId(String duelId) {
        this.duelId = duelId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
