package com.example.readon.datamodel.duel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DuelQuestionItemResponse implements Parcelable {

    private String question;
    private List<DuelAnswerItemResponse> answers = new ArrayList<>();

    public DuelQuestionItemResponse() {}

    public String getQuestion() {
        return question;
    }

    public List<DuelAnswerItemResponse> getAnswers() {
        return answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<DuelAnswerItemResponse> answers) {
        this.answers = answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeList(answers);
    }

    private DuelQuestionItemResponse(Parcel in) {
        question = in.readString();
        in.readList(answers, this.getClass().getClassLoader());
    }

    public static final Parcelable.Creator<DuelQuestionItemResponse> CREATOR = new Parcelable.Creator<DuelQuestionItemResponse>() {
        @Override
        public DuelQuestionItemResponse createFromParcel(Parcel source) {
            return new DuelQuestionItemResponse(source);
        }

        @Override
        public DuelQuestionItemResponse[] newArray(int size) {
            return new DuelQuestionItemResponse[size];
        }
    };
}
