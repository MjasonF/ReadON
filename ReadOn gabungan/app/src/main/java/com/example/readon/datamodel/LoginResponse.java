package com.example.readon.datamodel;

public class LoginResponse {
    private String userId;
    private Boolean isAdmin;

    public String getUserId() {
        return userId;
    }

    public Boolean getAdmin() {
        if (isAdmin == null) return false;
        return isAdmin;
    }
}
