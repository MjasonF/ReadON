package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.readon.model.Questions;
import com.example.readon.model.Reads;
import com.example.readon.service.APIClient;
import com.example.readon.service.APIService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {

    List<Questions> questions;

    TextView questionOne, questionTwo, questionThree, questionFour;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
    RadioButton btn1_q1, btn2_q1, btn3_q1, btn4_q1;
    RadioButton btn1_q2, btn2_q2, btn3_q2, btn4_q2;
    RadioButton btn1_q3, btn2_q3, btn3_q3, btn4_q3;
    RadioButton btn1_q4, btn2_q4, btn3_q4, btn4_q4;

    ArrayList<Boolean> bener = new ArrayList<>();

    public void resetJawaban() {
        bener = new ArrayList<>();
        // Anggap salah dulu semua.
        bener.add(false);
        bener.add(false);
        bener.add(false);
        bener.add(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questionOne = findViewById(R.id.q1);
        radioGroup1 = findViewById(R.id.radio_group1);
        btn1_q1 = findViewById(R.id.radio_btn1_q1);
        btn2_q1 = findViewById(R.id.radio_btn2_q1);
        btn3_q1 = findViewById(R.id.radio_btn3_q1);
        btn4_q1 = findViewById(R.id.radio_btn4_q1);

        questionTwo = findViewById(R.id.q2);
        radioGroup2 = findViewById(R.id.radio_group2);
        btn1_q2 = findViewById(R.id.radio_btn1_q2);
        btn2_q2 = findViewById(R.id.radio_btn2_q2);
        btn3_q2 = findViewById(R.id.radio_btn3_q2);
        btn4_q2 = findViewById(R.id.radio_btn4_q2);

        questionThree = findViewById(R.id.q3);
        radioGroup3 = findViewById(R.id.radio_group3);
        btn1_q3 = findViewById(R.id.radio_btn1_q3);
        btn2_q3 = findViewById(R.id.radio_btn2_q3);
        btn3_q3 = findViewById(R.id.radio_btn3_q3);
        btn4_q3 = findViewById(R.id.radio_btn4_q3);

        questionFour = findViewById(R.id.q4);
        radioGroup4 = findViewById(R.id.radio_group4);
        btn1_q4 = findViewById(R.id.radio_btn1_q4);
        btn2_q4 = findViewById(R.id.radio_btn2_q4);
        btn3_q4 = findViewById(R.id.radio_btn3_q4);
        btn4_q4 = findViewById(R.id.radio_btn4_q4);

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        fetchData(id);
    }

    public void onGetData() {
        ArrayList<RadioButton> buttons = new ArrayList<>();
        buttons.add(btn1_q1);
        buttons.add(btn2_q1);
        buttons.add(btn3_q1);
        buttons.add(btn4_q1);
        setQuestions(questions.get(0), "1. ", buttons, questionOne);

        buttons = new ArrayList<>();
        buttons.add(btn1_q2);
        buttons.add(btn2_q2);
        buttons.add(btn3_q2);
        buttons.add(btn4_q2);
        setQuestions(questions.get(1), "2. ", buttons, questionTwo);

        buttons = new ArrayList<>();
        buttons.add(btn1_q3);
        buttons.add(btn2_q3);
        buttons.add(btn3_q3);
        buttons.add(btn4_q3);
        setQuestions(questions.get(2), "3. ", buttons, questionThree);

        buttons = new ArrayList<>();
        buttons.add(btn1_q4);
        buttons.add(btn2_q4);
        buttons.add(btn3_q4);
        buttons.add(btn4_q4);
        setQuestions(questions.get(3), "4. ", buttons, questionFour);
    }

    public void setQuestions(Questions q, String text, ArrayList<RadioButton> buttons, TextView judul) {
        judul.setText(text + q.getQuestions());

        String[] intArray = { q.getAnswer(), q.getWronganswer1(), q.getWronganswer2(), q.getWronganswer3() };
        List<String> intList = Arrays.asList(intArray);
        Collections.shuffle(intList);
        intList.toArray(intArray);

        buttons.get(0).setText(intArray[0]);
        buttons.get(1).setText(intArray[1]);
        buttons.get(2).setText(intArray[2]);
        buttons.get(3).setText(intArray[3]);
    }

    public void fetchData(String id) {
        APIService service = APIClient.client();
        Call<List<Questions>> call = service.getquestions();

        call.enqueue(new Callback<List<Questions>>() {
            @Override
            public void onResponse(Call<List<Questions>> call, Response<List<Questions>> response) {

                if (!response.isSuccessful()){
                    System.out.println("Code: " + response.code());
                    return;
                }

                List<Questions> questionResponse = response.body();
                List<Questions> newQuestions = new ArrayList<>();


                if (questionResponse.size() != 0) {
                    for(int i = 0; i < questionResponse.size(); i++) {

                        if (questionResponse.get(i).getReads_Id().equals(id)) {
                            newQuestions.add(questionResponse.get(i));
                        }
                    }

                    questions = newQuestions;

                    if (questions.size() != 0) {
                        onGetData();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Questions>> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }

    public void checkAnswer(int nomor, RadioGroup group) {
        nomor = nomor - 1;

        RadioButton selectedRadioButton = (RadioButton)findViewById(group.getCheckedRadioButtonId());

        if (selectedRadioButton != null) {
            String jawaban = selectedRadioButton.getText().toString();
            boolean tes = jawaban.equals(questions.get(nomor).getAnswer());

            if(tes) {
                bener.set(nomor, true);
            }
        }

    }

    public void onAnswerOne(View view) {
        resetJawaban();

        checkAnswer(1, radioGroup1);
        checkAnswer(2, radioGroup2);
        checkAnswer(3, radioGroup3);
        checkAnswer(4, radioGroup4);
//
        int jumlah = 0;
        for (int i = 0; i < bener.size(); i++) {
            if (bener.get(i) == true) {
                jumlah++;
            }
        }
//
//        questionOne.setText(jumlah + " ");
        Intent i = new Intent(this, QuestionsResultActivity.class);
        i.putExtra("hasil", jumlah);
        startActivity(i);
        finish();
    }
}