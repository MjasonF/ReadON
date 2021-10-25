package com.example.readon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// import com.example.profile.R; // ini apa
import com.example.readon.model.TipsModel;
import com.example.readon.TipsItemAdapter;

import java.util.ArrayList;

public class TipsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TipsItemAdapter adapter;
    private ArrayList<TipsModel> tipsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.tipsRecyclerView);

        adapter = new TipsItemAdapter(this, tipsModelArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    void addData(){
        tipsModelArrayList = new ArrayList<>();
        tipsModelArrayList.add(new TipsModel("Goals","Set Yourself Small Goals", "Learning English is a long process, and it may take years to progress from one level to the next. That’s why it’s important to set yourself achievable goals."));
        tipsModelArrayList.add(new TipsModel("Schedule","Create a study schedule", "Ensure this schedule works around your daily responsibilities and make sure to keep it simple! Try breaking up your English language learning over the course of an entire day, which may make it feel less daunting."));
        tipsModelArrayList.add(new TipsModel("Practice","Practice a Little bit Everyday", "Even if it’s listening to an English podcast during your commute or practicing your favourite words while having your breakfast — every little bit of practice helps!"));
        tipsModelArrayList.add(new TipsModel("Discover Learning Type","Discover what type of learner you are: Auditory, visual or tactile", "Use this as a basis for how you spend time studying. For instance, a visual learner may benefit from flashcards, while an auditory learning may benefit from watching a television programme in English."));
        tipsModelArrayList.add(new TipsModel("Music","Turn on English Music", "Rhythms and rhymes have been found to stimulate the brain and improve learning and also pronunciation, word boundaries and vocabulary."));
        tipsModelArrayList.add(new TipsModel("Movies","Watch Movies with English Dialogue", "Movies are a great source of native conversation and vocabulary. Plus, the visual nature of film allows you to experience nonverbal context too — such as facial expressions and hand gestures — accompanying and contextualising the film’s dialogue. Above all, watching a film is a fun and motivational way to develop English language skills."));
        tipsModelArrayList.add(new TipsModel("Learning","Learn new Vocabulary", "As there are a lot of vocabulary existed, learning 10 or more vocabulary everyday helps to improve individual vocabulary."));
        tipsModelArrayList.add(new TipsModel("Track","Track Your Progress", "To gain a sense of accomplishment, it’s important to know where you’ve started. We’ve just introduced a new way to measure progress in English. Have a look at it to quickly discover what your areas of strength and weakness are."));
    }

}
