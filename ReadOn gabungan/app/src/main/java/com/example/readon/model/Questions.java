package com.example.readon.model;

import com.google.gson.annotations.SerializedName;

public class Questions {
    @SerializedName("_id")
    private String id;

    private String reads_Id;
    private String questions;
    private String answer;
    private String wronganswer1;
    private String wronganswer2;
    private String wronganswer3;

    public String getId() {
        return id;
    }

    public String getReads_Id() {
        return reads_Id;
    }

    public String getQuestions() {
        return questions;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWronganswer1() {
        return wronganswer1;
    }

    public String getWronganswer2() {
        return wronganswer2;
    }

    public String getWronganswer3() {
        return wronganswer3;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReads_Id(String reads_Id) {
        this.reads_Id = reads_Id;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setWronganswer1(String wronganswer1) {
        this.wronganswer1 = wronganswer1;
    }

    public void setWronganswer2(String wronganswer2) {
        this.wronganswer2 = wronganswer2;
    }

    public void setWronganswer3(String wronganswer3) {
        this.wronganswer3 = wronganswer3;
    }
}
