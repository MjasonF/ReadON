package com.example.readon.datamodel.duel;

import java.util.List;

public class DuelTextRequest {

    private String text;
    private List<DuelQuestionRequest> questions;

    public void setText(String text) {
        this.text = text;
    }

    public void setQuestions(List<DuelQuestionRequest> questions) {
        this.questions = questions;
    }
}
