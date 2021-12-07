package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.logging.Logger.global;

public class CreateNotesActivity extends AppCompatActivity {
    private Button datePicker, timePicker, notesSet;
    private TextView dateViewer, timeViewer;
    private EditText notesEditor;
    private Integer hours, months, days, years, minutes;
    NotesDatabase db;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        executorService = Executors.newSingleThreadExecutor();

        db = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "database-name").build();

        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        notesSet = findViewById(R.id.notes_set);
        dateViewer = findViewById(R.id.date_viewer);
        timeViewer = findViewById(R.id.time_viewer);
        notesEditor = findViewById(R.id.notes_input_desc);

        final Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                years = year;
                months = monthOfYear;
                days = dayOfMonth;
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateViewer.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();
            }
        });

        timePicker.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(CreateNotesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hours = selectedHour;
                    minutes = selectedMinute;
                    timeViewer.setText( selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        notesSet.setOnClickListener(v -> {
            String value = notesEditor.getText().toString();
            Notes notes = new Notes(value, days, months, years, hours, minutes);
            NotesQuery notesQuery = db.NotesQuery();
            executorService.execute(()->{
                notesQuery.insertAll(notes);
            });

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, years);
            calendar.set(Calendar.MONTH, months);
            calendar.set(Calendar.DAY_OF_MONTH, days);
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            final int id = (int) System.currentTimeMillis();
            Intent intentAlarm = new Intent(this, AlarmReceiver.class);
            intentAlarm.putExtra("desc", value);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);


            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar  .getTimeInMillis(), pendingIntent);

            Toast.makeText(CreateNotesActivity.this, "New Note Created", Toast.LENGTH_LONG).show();
        });
    }
}
