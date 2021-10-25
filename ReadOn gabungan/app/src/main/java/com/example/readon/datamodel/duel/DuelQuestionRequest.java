package com.example.readon.datamodel.duel;

import java.util.List;

public class DuelQuestionRequest {

    private String question;
    private List<DuelAnswerRequest> answers;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<DuelAnswerRequest> answers) {
        this.answers = answers;
    }
}
