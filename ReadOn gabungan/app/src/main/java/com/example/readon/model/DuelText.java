package com.example.readon.model;

import java.util.List;

public class DuelText {

    private String text;
    private List<DuelQuestion> questions;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DuelQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DuelQuestion> questions) {
        this.questions = questions;
    }
}
