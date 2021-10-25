package com.example.readon.datamodel.duel;

import com.google.gson.annotations.SerializedName;

public class DuelTextResponse {

    @SerializedName("dueltext_id")
    private DuelTextItemResponse text;

    public DuelTextItemResponse getText() {
        return text;
    }
}
