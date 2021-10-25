package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.readon.datamodel.duel.DuelAnswerItemResponse;
import com.example.readon.datamodel.duel.DuelQuestionItemResponse;

import java.util.ArrayList;
import java.util.List;

public class DuelAnswerActivity extends AppCompatActivity implements DuelAnswerHandler {
    
    private long currentScore = 0L;
    private int currentPosition = 0;

    private List<DuelQuestionItemResponse> questions = new ArrayList<>();
    private String duelId;
    private String text;

    public static final String QUESTIONS = "questions";
    public static final String DUEL_ID = "duel_id";
    public static final String TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel_answer);

        questions = getIntent().getParcelableArrayListExtra(QUESTIONS);
        duelId = getIntent().getStringExtra(DUEL_ID);
        text = getIntent().getStringExtra(TEXT);

        if (questions == null) {
            questions = new ArrayList<>();
        }

        loadInitialQuestion();
    }

    @Override
    public void onNext(long score) {
        currentScore += score;
        currentPosition += 1;

        if (currentPosition >= questions.size()) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(HomeActivity.HOME_MESSAGE, "Duel result can be seen after both user have finished the duel");
            startActivity(intent);
            return;
        }

        loadNextQuestion();
    }

    private void loadInitialQuestion() {
        boolean isLast = questions.size() == 1;

        DuelAnswerFragment fragment = DuelAnswerFragment.newInstance(
            text,
            questions.get(currentPosition),
            currentScore,
            isLast,
            duelId
        );

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_question_container, fragment);
        transaction.commit();
    }

    private void loadNextQuestion() {
        boolean isLast = currentPosition == questions.size() - 1;

        DuelAnswerFragment fragment = DuelAnswerFragment.newInstance(
            text,
            questions.get(currentPosition),
            currentScore,
            isLast,
            duelId
        );

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_question_container, fragment);
        transaction.commit();
    }
}