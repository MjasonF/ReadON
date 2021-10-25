package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.duel.DuelTextItemResponse;
import com.example.readon.datamodel.duel.DuelTextResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuelStartActivity extends AppCompatActivity {

    private TextView tvDuelText;
    private Button btnFinishReading;
    private ProgressBar progressBar;
    private Group contentGroup;

    private DuelTextItemResponse duelTextResponse;

    public static final String DUEL_UNIQUE_ID = "duel-unique-id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel_start);

        String duelId = getIntent().getStringExtra(DUEL_UNIQUE_ID);

        tvDuelText = findViewById(R.id.tv_duel_text);
        btnFinishReading = findViewById(R.id.btn_finish_reading);
        progressBar = findViewById(R.id.progress_bar);
        contentGroup = findViewById(R.id.content_group);

        tvDuelText.setMovementMethod(new ScrollingMovementMethod());

        btnFinishReading.setOnClickListener(v -> {
            Intent intent = new Intent(this, DuelAnswerActivity.class);
            intent.putParcelableArrayListExtra(DuelAnswerActivity.QUESTIONS, new ArrayList<>(duelTextResponse.getQuestions()));
            intent.putExtra(DuelAnswerActivity.DUEL_ID, duelId);
            intent.putExtra(DuelAnswerActivity.TEXT, duelTextResponse.getText());
            startActivity(intent);
        });

        loadDuelQuestion(duelId);
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            contentGroup.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            contentGroup.setVisibility(View.VISIBLE);
        }
    }

    private void loadDuelQuestion(String duelId) {
        showLoading(true);
        SendDuelRequest request = new SendDuelRequest();
        request.setDuelId(duelId);
        APIClient.client().getDuelQuestionData(request).enqueue(new Callback<APIResponse<List<DuelTextResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DuelTextResponse>>> call, Response<APIResponse<List<DuelTextResponse>>> response) {
                if (response.isSuccessful()) {
                    try {
                        DuelTextResponse result = response.body().getData().get(0);
                        duelTextResponse = result.getText();
                        String duelText = duelTextResponse.getText() != null && !duelTextResponse.getText().equals("") ? duelTextResponse.getText() : "";
                        tvDuelText.setText(duelText);
                    } catch (Exception ex) {
                        tvDuelText.setText("Something went wrong");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(DuelStartActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(DuelStartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                showLoading(false);
            }

            @Override
            public void onFailure(Call<APIResponse<List<DuelTextResponse>>> call, Throwable t) {
                Toast.makeText(DuelStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        });
    }
}