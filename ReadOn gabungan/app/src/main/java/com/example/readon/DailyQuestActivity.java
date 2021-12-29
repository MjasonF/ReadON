package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.mission.MissionRequest;
import com.example.readon.model.QuestItem;
import com.example.readon.model.QuestModel;
import com.example.readon.model.Reads;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;
import com.example.readon.service.MissionCompleteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyQuestActivity extends AppCompatActivity {
    private QuestAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quest);

        recyclerView = findViewById(R.id.textListsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestAdapter(this);
        recyclerView.setAdapter(adapter);


        getMissions();

    }

    public void onSuccessGetData(List<QuestItem> quests) {
        ArrayList<QuestModel> newQuests = new ArrayList<>();

        for (QuestItem quest : quests) {
            QuestModel newQuest = new QuestModel();

            newQuest.name = quest.missionName;
            newQuest.status = quest.completed ? "Completed" : "Uncompleted";

            newQuests.add(newQuest);
        }

        adapter.addItem(newQuests);
    }

    public void getMissions() {
        String userId = new AppPreference(this).getUserId();

        MissionRequest missionRequest = new MissionRequest();
        missionRequest.userId = userId;

        APIClient.client()
                .getMissions(missionRequest)
                .enqueue(new Callback<APIResponse<List<QuestItem>>>() {
                    @Override
                    public void onResponse(Call<APIResponse<List<QuestItem>>> call, Response<APIResponse<List<QuestItem>>> response) {
                        onSuccessGetData(response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<APIResponse<List<QuestItem>>> call, Throwable t) {
                    }
                });
    }
}