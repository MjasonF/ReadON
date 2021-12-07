package com.example.readon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgendaActivity extends AppCompatActivity {

    private Button addNotes;
    private CalendarView calendar;
    private RecyclerView recyclerView;
    private NotesDatabase db;
    private ArrayList<Notes> notesList;
    private NotesAdapter notesAdapter;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        addNotes = findViewById(R.id.notes_add);
        calendar = findViewById(R.id.agenda_calendar);
        recyclerView = findViewById(R.id.notes_list);
        executorService = Executors.newSingleThreadExecutor();
        db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "database-name").build();
        notesList = new ArrayList<>();
        setAdapter();

        addNotes.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateNotesActivity.class);
            startActivity(intent);
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                NotesQuery notesQuery = db.NotesQuery();
                executorService.execute(() -> {
                    ArrayList<Notes> notes = new ArrayList<>(notesQuery.getAll(dayOfMonth, month, year));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notesAdapter.setData(notes);
                        }
                    });
                });

            }
        });

    }
    private void setAdapter(){
        notesAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);
    }
}