package com.example.readon.model;

public class Request {
    private String request, type, time;

    public Request(String request, String type, String time) {
        this.request = request;
        this.type = type;
        this.time = time;
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
}
