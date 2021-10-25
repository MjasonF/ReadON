package com.example.readon.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.readon.BR;

import java.util.List;

public class DuelQuestion extends BaseObservable {

    private String question;
    private List<DuelAnswer> answers;

    @Bindable
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
        notifyPropertyChanged(BR.question);
    }

    @Bindable
    public List<DuelAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<DuelAnswer> answers) {
        this.answers = answers;
        notifyPropertyChanged(BR.answers);
    }
}
