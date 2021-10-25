package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuestionsResultActivity extends AppCompatActivity {

    TextView text;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_result);

        Intent i = getIntent();
        int hasil = i.getIntExtra("hasil", 0);
//        String id = i.getIntExtra("hasil", 0);

        text = findViewById(R.id.textView5);
        text.setText("You managed to answer\n" + hasil + " out of 4 questions");
    }

    public void goToText(View view) {
        finish();
    }

//    public void goToContentList(View view) {
//        Intent i = new Intent(this, LibraryActivity.class);
//        startActivity(i);
//        finish();
//    }
}