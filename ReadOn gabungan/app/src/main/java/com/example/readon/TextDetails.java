package com.example.readon;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextDetails extends AppCompatActivity {
    TextView textContent;
    private Button moveQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_details);

        textContent = findViewById(R.id.contentOfText);
        Intent i = getIntent();
        String title = i.getStringExtra("titleOfText");
        String content = i.getStringExtra("contentOfText");

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