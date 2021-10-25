package com.example.readon.datamodel;

public class APIResponse <T>{
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
