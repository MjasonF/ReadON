package com.example.readon.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.readon.BR;

public class DuelAnswer extends BaseObservable {

    private String answer;
    private Boolean questionAnswer = false;

    @Bindable
    public String getAnswer() {
        if (answer == null || answer.isEmpty()) return "";
        return answer;
    }

    public void setAnswer(Boolean answer) {
        questionAnswer = answer;
        notifyPropertyChanged(BR.questionAnswer);
    }

    @Bindable
    public Boolean getQuestionAnswer() {
        return questionAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        notifyPropertyChanged(BR.answer);
    }
}
