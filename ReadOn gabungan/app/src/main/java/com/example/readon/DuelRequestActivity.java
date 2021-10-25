package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.duel.DuelResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.model.Duel;
import com.example.readon.model.User;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuelRequestActivity extends AppCompatActivity {

    private RecyclerView rvDuelRequest;
    private DuelRequestAdapter adapter;
    private AppPreference appPreference;
    private ProgressBar progressBar;
    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel_request);

        appPreference = new AppPreference(this);

        progressBar = findViewById(R.id.progress_bar);
        rvDuelRequest = findViewById(R.id.recyclerView);
        initAdapter();

        tvNoData = findViewById(R.id.tv_no_data);

        loadDuelRequest();
    }

    private void initAdapter() {
        adapter = new DuelRequestAdapter(new DuelRequestAdapter.DuelRequestAction() {
            @Override
            public void onAccept(Duel duel) {
                acceptDuel(duel);
            }

            @Override
            public void onDecline(Duel duel) {
                declineDuel(duel);
            }
        });
        rvDuelRequest.setLayoutManager(new LinearLayoutManager(this));
        rvDuelRequest.setAdapter(adapter);
    }

    private void acceptDuel(Duel duel) {
        progressBar.setVisibility(View.VISIBLE);
        SendDuelRequest request = new SendDuelRequest();
        request.setUserId(appPreference.getUserId());
        request.setOpponentId(duel.getOpponentUserId());
        request.setDuelId(duel.getDuelId());
        APIClient.client().acceptDuel(request).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(DuelRequestActivity.this, message, Toast.LENGTH_SHORT).show();
                    loadDuelRequest();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(DuelRequestActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(DuelRequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(DuelRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void declineDuel(Duel duel) {
        progressBar.setVisibility(View.VISIBLE);
        SendDuelRequest request = new SendDuelRequest();
        request.setDuelId(duel.getDuelId());
        APIClient.client().declineDuel(request).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(DuelRequestActivity.this, message, Toast.LENGTH_SHORT).show();
                    loadDuelRequest();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(DuelRequestActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(DuelRequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(DuelRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadDuelRequest() {
        progressBar.setVisibility(View.VISIBLE);
        User user = new User();
        user.setUserId(appPreference.getUserId());
        APIClient.client().viewDuelRequest(user).enqueue(new Callback<APIResponse<List<DuelResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DuelResponse>>> call, Response<APIResponse<List<DuelResponse>>> response) {
                if (response.body() != null) {
                    List<DuelResponse> duelResponse = response.body().getData();
                    if (duelResponse == null) {
                        duelResponse = new ArrayList<>();
                    }
                    handleResponse(duelResponse);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<APIResponse<List<DuelResponse>>> call, Throwable t) {
                Toast.makeText(DuelRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void handleResponse(List<DuelResponse> duelResponse) {
        if (duelResponse.size() == 0) {
            tvNoData.setVisibility(View.VISIBLE);
        }
        else tvNoData.setVisibility(View.GONE);
        ArrayList<Duel> requests = new ArrayList<>();
        for (DuelResponse itemResponse: duelResponse) {
            Duel duel = new Duel();
            duel.setDuelId(itemResponse.getDuelId());
            duel.setOpponentUsername(itemResponse.getUser().getUsername());
            duel.setOpponentUserId(itemResponse.getUser().getUserId());
            duel.setType("Point Competition");
            requests.add(duel);
        }
        adapter.setData(requests);
    }
}