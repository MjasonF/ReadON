package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.readon.model.Reads;
import com.example.readon.service.APIClient;
import com.example.readon.service.APIService;
import com.example.readon.service.ReadHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextAdapter adapter;
    List<Reads> list = new ArrayList<Reads>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> contents = new ArrayList<String>();

        recyclerView = findViewById(R.id.textListsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TextAdapter(this, titles, contents);
        recyclerView.setAdapter(adapter);

        ReadHelper.fetchData(adapter, "60c1d4c5618bc1373cd53207");
    }
}