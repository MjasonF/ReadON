package com.example.readon.model;

public class Duel {

    private String duelId;
    private String title, request, type, time;
    private Integer result;
    private Boolean expanded;
    private Integer point;
    private Integer opponentPoint;
    private Boolean winner;
    private Boolean isDraw;
    private String opponentUsername;
    private String opponentUserId;
    private String createdAt;

    public Boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duel() {}

    public String getDuelId() {
        return duelId;
    }

    public void setDuelId(String duelId) {
        this.duelId = duelId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getOpponentPoint() {
        return opponentPoint;
    }

    public void setOpponentPoint(Integer opponentPoint) {
        this.opponentPoint = opponentPoint;
    }

    public Boolean getDraw() {
        return isDraw;
    }

    public void setDraw(Boolean draw) {
        isDraw = draw;
    }

    public Boolean getWinner() {
        return winner;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public String getOpponentUserId() {
        return opponentUserId;
    }

    public void setOpponentUsername(String opponentUsername) {
        this.opponentUsername = opponentUsername;
    }

    public void setOpponentUserId(String opponentUserId) {
        this.opponentUserId = opponentUserId;
    }
}
