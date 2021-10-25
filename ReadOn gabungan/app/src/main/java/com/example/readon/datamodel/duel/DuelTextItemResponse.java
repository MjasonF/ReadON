package com.example.readon.datamodel.duel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DuelTextItemResponse implements Parcelable {

    @SerializedName("_id")
    private String textId;

    private String text;

    private List<DuelQuestionItemResponse> questions = new ArrayList<>();

    public String getTextId() {
        return textId;
    }

    public String getText() {
        return text;
    }

    public List<DuelQuestionItemResponse> getQuestions() {
        return questions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textId);
        dest.writeString(text);
        dest.writeList(questions);
    }

    private DuelTextItemResponse(Parcel in) {
        textId = in.readString();
        text = in.readString();
        in.readList(questions, this.getClass().getClassLoader());
    }

    public static final Parcelable.Creator<DuelTextItemResponse> CREATOR = new Parcelable.Creator<DuelTextItemResponse>() {
        @Override
        public DuelTextItemResponse createFromParcel(Parcel source) {
            return new DuelTextItemResponse(source);
        }

        @Override
        public DuelTextItemResponse[] newArray(int size) {
            return new DuelTextItemResponse[size];
        }
    };
}
