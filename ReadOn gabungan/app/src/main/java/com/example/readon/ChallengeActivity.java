package com.example.readon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.readon.model.Challenge;

import java.util.ArrayList;

public class ChallengeActivity extends AppCompatActivity {

    private ArrayList<Challenge> challengeList;
    private RecyclerView challengeView;
    private Button testbtn;
    private Integer totalRead = 0;
    private static final Integer maxProgress = 10;
    private ChallengeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        challengeView = findViewById(R.id.challengeView);
        challengeList = new ArrayList<>();
        testbtn = findViewById(R.id.test);

        setChallengeData();
        setAdapter();

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalRead == 10){
                    return;
                }
                totalRead += 1;
                Integer left = (maxProgress - totalRead) * 10;
                Integer result = 100 - left;

                Challenge celen = challengeList.get(0);
                celen.setProgress(result);
                adapter.notifyDataSetChanged();
                if(totalRead == 10){
                    AlertDialog alert = new AlertDialog.Builder(ChallengeActivity.this)
                            .setTitle("Achievement Unlocked")
                            .setMessage("Book Worm\n\n"+celen.getReward()+"$")
                            .setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                            }).create();
                    alert.show();
                }
            }
        });

    }

    private void setAdapter(){
        adapter = new ChallengeAdapter(challengeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        challengeView.setLayoutManager(layoutManager);
        challengeView.setItemAnimator(new DefaultItemAnimator());
        challengeView.setAdapter(adapter);
    }

    private void setChallengeData(){
        challengeList.add(new Challenge("1","Book Worm", "Read 10 Books", 0, 100));
        challengeList.add(new Challenge("2","Book Duelist", "Win 10 Duels", 0, 100));

    }
}