package com.example.readon.datamodel.duel;

import android.os.Parcel;
import android.os.Parcelable;

public class DuelAnswerItemResponse implements Parcelable {

    private String answer;
    private Boolean isAnswer;

    public DuelAnswerItemResponse() {}

    public String getAnswer() {
        return answer;
    }

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsAnswer(Boolean answer) {
        this.isAnswer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeInt(isAnswer ? 1 : 0);
    }

    private DuelAnswerItemResponse(Parcel in) {
        answer = in.readString();
        isAnswer = in.readInt() == 1;
    }

    public static final Parcelable.Creator<DuelAnswerItemResponse> CREATOR = new Parcelable.Creator<DuelAnswerItemResponse>() {
        @Override
        public DuelAnswerItemResponse createFromParcel(Parcel source) {
            return new DuelAnswerItemResponse(source);
        }

        @Override
        public DuelAnswerItemResponse[] newArray(int size) {
            return new DuelAnswerItemResponse[size];
        }
    };
}
