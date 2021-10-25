package com.example.readon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.duel.DuelAnswerRequest;
import com.example.readon.datamodel.duel.DuelQuestionRequest;
import com.example.readon.datamodel.duel.DuelTextRequest;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.model.DuelAnswer;
import com.example.readon.model.DuelQuestion;
import com.example.readon.service.APIClient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDuelTextActivity extends AppCompatActivity {

    private EditText etText;
    private RecyclerView rvQuestions;
    private ExtendedFloatingActionButton fabAddQuestion;
    private ProgressBar progressBar;

    private List<DuelQuestion> questions;

    private DuelTextQuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_duel_text);

        etText = findViewById(R.id.et_text);
        rvQuestions = findViewById(R.id.rv_questions);
        fabAddQuestion = findViewById(R.id.fab_add_question);
        progressBar = findViewById(R.id.progress_bar);

        questions = new ArrayList<>();

        fabAddQuestion.setOnClickListener(v -> {
            DuelQuestion question = new DuelQuestion();
            question.setQuestion("");
            question.setAnswers(new ArrayList<>());
            questions.add(question);
            questionAdapter.notifyItemInserted(questions.size() - 1);
        });

        initQuestionAdapter();
    }

    private void initQuestionAdapter() {
        questionAdapter = new DuelTextQuestionAdapter(questions, new DuelTextQuestionAdapter.DuelQuestionAction() {
            @Override
            public void onQuestionRemove(int position) {
                questions.remove(position);
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNewAnswerAdded(int position) {
                DuelAnswer answer = new DuelAnswer();
                answer.setAnswer("");
                answer.setAnswer(false);
                questions.get(position).getAnswers().add(answer);
                questionAdapter.notifyItemChanged(position);
            }
        });
        rvQuestions.setLayoutManager(new LinearLayoutManager(this));
        rvQuestions.setAdapter(questionAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_text_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                addText();
                break;
        }
        return true;
    }

    private void addText() {
        progressBar.setVisibility(View.VISIBLE);
        DuelTextRequest request = new DuelTextRequest();
        String text = etText.getText() == null ? "" : etText.getText().toString();
        request.setText(text);
        request.setQuestions(mapIntoRequest());
        APIClient.client().addDuelText(request).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(AddDuelTextActivity.this, "Add Text Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(AddDuelTextActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(AddDuelTextActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(AddDuelTextActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private List<DuelQuestionRequest> mapIntoRequest() {
        List<DuelQuestionRequest> list = new ArrayList<>();
        for (DuelQuestion question: questions) {
            DuelQuestionRequest duelQuestionRequest = new DuelQuestionRequest();
            duelQuestionRequest.setQuestion(question.getQuestion());
            duelQuestionRequest.setAnswers(mapIntoAnswerRequest(question.getAnswers()));
            list.add(duelQuestionRequest);
        }
        return list;
    }

    private List<DuelAnswerRequest> mapIntoAnswerRequest(List<DuelAnswer> answers) {
        List<DuelAnswerRequest> list = new ArrayList<>();
        for (DuelAnswer answer: answers) {
            DuelAnswerRequest duelAnswerRequest = new DuelAnswerRequest();
            duelAnswerRequest.setAnswer(answer.getAnswer());
            duelAnswerRequest.setAnswer(answer.getQuestionAnswer());
            list.add(duelAnswerRequest);
        }
        return list;
    }
}