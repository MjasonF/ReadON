package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    EditText inputText;
    Button convertBt, clearBt;
    TextToSpeech textToSpeech;

    @Override
    protected void onPause() {
        if (textToSpeech != null){
            textToSpeech.shutdown();
            textToSpeech.stop();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        inputText = findViewById(R.id.input);
        convertBt = findViewById(R.id.convert_bt);
        clearBt = findViewById(R.id.clear_bt);

        textToSpeech =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int lang =textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        convertBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s =inputText.getText().toString();
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        clearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText("");
            }
        });

    }
}