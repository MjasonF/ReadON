package com.example.readon.service;

import android.content.Context;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.mission.MissionRequest;
import com.example.readon.model.QuestItem;
import com.example.readon.pref.AppPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionCompleteService {
    private AppPreference appPreference;
    private Context context;

    public MissionCompleteService(Context context) {
        this.appPreference = new AppPreference((context));
        this.context = context;
    }

    public void completeMission(int missionId) {
        String userId = appPreference.getUserId();

        MissionRequest missionRequest = new MissionRequest();
        missionRequest.userId = userId;
        missionRequest.dailyQuestId = missionId;

        APIClient.client()
                .completeMission(missionRequest)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
    }

}
