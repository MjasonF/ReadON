package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;

public class LibraryActivity extends AppCompatActivity {

    private Button moveBooks;
    private Button moveStories;
    private Button moveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        moveBooks = findViewById(R.id.buttonBooks);
        moveBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, BooksActivity.class);
                startActivity(intent);
            }
        });

        moveStories = findViewById(R.id.buttonStories);
        moveStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, StoriesActivity.class);
                startActivity(intent);
            }
        });

        moveText = findViewById(R.id.buttonText);
        moveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, TextActivity.class);
                startActivity(intent);
            }
        });
    }
}