package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.UserItemResponse;
import com.example.readon.datamodel.duel.DuelResponse;
import com.example.readon.datamodel.duel.DuelTextResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.model.Duel;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuelHistoryActivity extends AppCompatActivity {
    private ArrayList<Duel> duelList;
    private RecyclerView recyclerView;
    private DuelHistoryAdapter historyAdapter;
    private Group contentGroup;
    private ProgressBar progressBar;

    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel_history);

        appPreference = new AppPreference(this);

        progressBar = findViewById(R.id.progress_bar);
        contentGroup = findViewById(R.id.content_group);
        recyclerView = findViewById(R.id.recyclerView);
        duelList = new ArrayList<>();

        setAdapter();
        loadHistory();

    }

    private void setAdapter(){
        historyAdapter = new DuelHistoryAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            contentGroup.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            contentGroup.setVisibility(View.VISIBLE);
        }
    }

    private void loadHistory() {
        showLoading(true);
        SendDuelRequest request = new SendDuelRequest();
        request.setUserId(appPreference.getUserId());
        APIClient.client().getDuelHistory(request).enqueue(new Callback<APIResponse<List<DuelResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DuelResponse>>> call, Response<APIResponse<List<DuelResponse>>> response) {
                if (response.isSuccessful()) {
                    List<DuelResponse> results = response.body().getData();
                    handleResponse(results);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(DuelHistoryActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(DuelHistoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                showLoading(false);
            }

            @Override
            public void onFailure(Call<APIResponse<List<DuelResponse>>> call, Throwable t) {
                Toast.makeText(DuelHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        });
    }

    private void handleResponse(List<DuelResponse> results) {
        List<Duel> duels = new ArrayList<>();
        for (DuelResponse duelResponse: results) {

            boolean isOpponentCurrentUser = appPreference.getUserId().equals(duelResponse.getOpponent().getUserId());

            Duel duel = new Duel();
            duel.setDuelId(duelResponse.getDuelId());
            duel.setOpponentUsername(isOpponentCurrentUser ? duelResponse.getUser().getUsername() : duelResponse.getOpponent().getUsername());
            duel.setPoint(isOpponentCurrentUser ? duelResponse.getOpponentPoint() : duelResponse.getPoint());
            duel.setOpponentPoint(isOpponentCurrentUser ? duelResponse.getPoint() : duelResponse.getOpponentPoint());

            String date = duelResponse.getCreatedAt();

            try {
                SimpleDateFormat base = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                Date parsedDate = base.parse(duelResponse.getCreatedAt());
                SimpleDateFormat formatted = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US);
                date = formatted.format(parsedDate);
            } catch (Exception e) {
                Log.e("<ERROR>", "handleResponse: " + e.getMessage());
            }

            duel.setCreatedAt(date);
            duel.setDraw(duelResponse.getWinner() == null);
            duel.setWinner(null);

            UserItemResponse user = isOpponentCurrentUser ? duelResponse.getOpponent() : duelResponse.getUser();
            if (duelResponse.getWinner() != null && duelResponse.getWinner().equals(user.getUserId())) {
                duel.setWinner(true);
            }
            else if (duelResponse.getWinner() != null && !duelResponse.getWinner().equals(user.getUserId())) {
                duel.setWinner(false);
            }

            duels.add(duel);
        }
        historyAdapter.setData(duels);
    }
}