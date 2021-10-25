package com.example.readon;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.duel.DuelAnswerItemResponse;
import com.example.readon.datamodel.duel.DuelQuestionItemResponse;
import com.example.readon.datamodel.duel.DuelResponse;
import com.example.readon.datamodel.duel.DuelTextResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuelAnswerFragment extends Fragment {

    private TextView tvDuelQuestion, tvCountDown, tvScore, tvDuelText;
    private RadioGroup rgAnswers;
    private Button btnNext;
    private ProgressBar progressBar;
    private Group contentGroup;
    private ScrollView scrollView;

    private CountDownTimer countDownTimer;

    private DuelQuestionItemResponse question;
    private long currentScore;
    private boolean isLast;
    private String duelId;
    private String text;

    private DuelAnswerHandler mHandler;
    private AppPreference appPreference;

    private final static String QUESTION = "question";
    private final static String CURRENT_SCORE = "current_score";
    private final static String IS_LAST = "is_last";
    private final static String DUEL_ID = "duel_id";
    private final static String TEXT = "text";
    private final static Long QUESTION_COUNTDOWN = 20000L;
    private final static Long COUNTDOWN_INTERVAL = 1000L;

    public static DuelAnswerFragment newInstance(String text, DuelQuestionItemResponse question, long currentScore, boolean isLast, String duelId) {
        Bundle args = new Bundle();
        args.putParcelable(QUESTION, question);
        args.putLong(CURRENT_SCORE, currentScore);
        args.putBoolean(IS_LAST, isLast);
        args.putString(DUEL_ID, duelId);
        args.putString(TEXT, text);
        DuelAnswerFragment fragment = new DuelAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_duel_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appPreference = new AppPreference(requireContext());

        tvDuelQuestion = view.findViewById(R.id.tv_duel_question);
        tvCountDown = view.findViewById(R.id.tv_countdown);
        tvScore = view.findViewById(R.id.tv_score);
        tvDuelText = view.findViewById(R.id.tv_duel_text);
        tvDuelText.setMovementMethod(new ScrollingMovementMethod());

        rgAnswers = view.findViewById(R.id.rg_answers);

        btnNext = view.findViewById(R.id.btn_next);
        progressBar = view.findViewById(R.id.progress_bar);
        contentGroup = view.findViewById(R.id.content_group);

        scrollView = view.findViewById(R.id.scrollView);

        question = getArguments().getParcelable(QUESTION);
        currentScore = getArguments().getLong(CURRENT_SCORE);
        isLast = getArguments().getBoolean(IS_LAST);
        duelId = getArguments().getString(DUEL_ID);
        text = getArguments().getString(TEXT);

        tvDuelText.setText(text);

        if (isLast) {
            btnNext.setText("Finish Duel");
        }

        btnNext.setOnClickListener(v -> {
            handleNextQuestion();
        });

        initTimer();
        loadQuestionAndOptions();
        startTimer();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvDuelText.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        tvDuelText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvDuelText.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mHandler = (DuelAnswerHandler) context;
    }

    private void loadQuestionAndOptions() {
        showLoading(true);
        tvDuelQuestion.setText(question.getQuestion());
        tvScore.setText("Your Current Score: " + currentScore);

        rgAnswers.removeAllViews();

        int radioId = 0;
        for (DuelAnswerItemResponse answer : question.getAnswers()) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setId(radioId);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setBackground(null);
            radioButton.setText(answer.getAnswer());

            if (radioId == 0) {
                radioButton.setChecked(true);
            }

            radioId++;

            rgAnswers.addView(radioButton, layoutParams);
        }

        showLoading(false);
    }

    private void initTimer() {
        countDownTimer = new CountDownTimer(QUESTION_COUNTDOWN, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                handleNextQuestion();
            }
        };
    }

    private void startTimer() {
        countDownTimer.start();
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

    private void handleNextQuestion() {
        int currentCountDown = Integer.parseInt(tvCountDown.getText().toString());
        if (currentCountDown == 0) {
            currentCountDown = 1;
        }

        int radioButtonId = rgAnswers.getCheckedRadioButtonId();
        View checkedRadioButton = rgAnswers.findViewById(radioButtonId);
        int idx = rgAnswers.indexOfChild(checkedRadioButton);

        countDownTimer.cancel();

        if (question.getAnswers().get(idx).getIsAnswer()) {
            int score = 10 * currentCountDown;
            if (isLast) {
                updateDuelStatus(score);
            } else {
                updateScore(score);
            }
        }
        else {
            if (isLast) {
                updateDuelStatus(0);
            }
            else {
                updateScore(0);
            }
        }
    }

    private void updateDuelStatus(int score) {
        showLoading(true);
        SendDuelRequest request = new SendDuelRequest();
        request.setDuelId(duelId);
        request.setUserId(appPreference.getUserId());
        APIClient.client().updateDuelStatus(request).enqueue(new Callback<APIResponse<DuelResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<DuelResponse>> call, Response<APIResponse<DuelResponse>> response) {
                if (response.isSuccessful()) {
                    updateScore(score);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(requireContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<DuelResponse>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        });
    }

    private void updateScore(int score) {
        showLoading(true);
        SendDuelRequest request = new SendDuelRequest();
        request.setDuelId(duelId);
        request.setUserId(appPreference.getUserId());
        request.setPoint(score);
        APIClient.client().updateScore(request).enqueue(new Callback<APIResponse<DuelResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<DuelResponse>> call, Response<APIResponse<DuelResponse>> response) {
                if (response.isSuccessful()) {
                    mHandler.onNext(score);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(requireContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                showLoading(false);
            }

            @Override
            public void onFailure(Call<APIResponse<DuelResponse>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                showLoading(false);
            }
        });
    }
}
