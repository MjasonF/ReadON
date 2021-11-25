package com.example.readon;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readon.pref.AppPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TextDetails extends AppCompatActivity {
    TextView textContent;
    private Button moveQuestions;
    FloatingActionButton fabBtn;
    Integer currentFontSize;
    Integer currentIndex;
    AppPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_details);

        textContent = findViewById(R.id.contentOfText);
        Intent i = getIntent();
        String title = i.getStringExtra("titleOfText");
        String content = i.getStringExtra("contentOfText");

        pref = new AppPreference(this);

        currentIndex = pref.getFontSize();

        if(currentIndex == 0){
            currentFontSize = 17;
        }else if(currentIndex == 1){
            currentFontSize = 19;
        }else if(currentIndex == 2){
            currentFontSize = 21;
        }

        textContent.setTextSize(currentFontSize);

        fabBtn = findViewById(R.id.btn_fab_text);
        fabBtn.setOnClickListener(v -> {
            if(currentIndex <2){
                currentIndex += 1;
            }else if(currentIndex == 2){
                currentIndex = 0;
            }
            pref.saveFontSize(currentIndex);
            if(currentIndex == 0){
                currentFontSize = 17;
            }else if(currentIndex == 1){
                currentFontSize = 19;
            }else if(currentIndex == 2){
                currentFontSize = 21;
            }
            textContent.setTextSize(currentFontSize);
        });

        getSupportActionBar().setTitle(title);

        textContent.setText(content  + "\n\n\n" );
        textContent.setMovementMethod(new ScrollingMovementMethod());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        moveQuestions = findViewById(R.id.button4);
        moveQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.readon.TextDetails.this, QuestionsActivity.class);

                Intent i = getIntent();
                String id = i.getStringExtra("id");
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


    }
}