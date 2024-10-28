package com.example.calendarapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {
    private EditText eventNameET;
    private Button eventDateStart, eventTimeStart, eventDateEnd, eventTimeEnd;
    private DatePickerDialog datePickerDialog;
    private LocalTime time;
    private LocalTime timeEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.eventMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initwidgets();
        time = LocalTime.now();
        timeEnd = time;
        LocalDate currentDate = CalendarUtils.selectedDate;
        CalendarUtils.selectedDateEnd = currentDate; // Initialize selectedDateEnd
        eventDateStart.setText("Dátum: " + currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth());
        eventTimeStart.setText("Idő: " + time.getHour() + ":" + time.getMinute());
        eventDateEnd.setText("Dátum: " + currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth());
        eventTimeEnd.setText("Idő: " + time.getHour() + ":" + time.getMinute());

    }

    private void initwidgets() {
        eventNameET = findViewById(R.id.eventName);
        eventDateStart = findViewById(R.id.eventDateStart);
        eventTimeStart = findViewById(R.id.eventTimeStart);
        eventDateEnd = findViewById(R.id.eventDateEnd);
        eventTimeEnd = findViewById(R.id.eventTimeEnd);
    }

    public void openDatePickerStart(View view) {
        datePickerDialog = new DatePickerDialog(EventActivity.this, (view1, year, month, dayOfMonth) -> {
            LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            eventDateStart.setText("Dátum: " + "Dátum: " + CalendarUtils.selectedDate.getYear() + "-" + CalendarUtils.selectedDate.getMonthValue() + "-" + CalendarUtils.selectedDate.getDayOfMonth());
            CalendarUtils.selectedDate = selectedDate;
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void openTimePickerStart(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this, (view1, hourOfDay, minute) -> {
            time = LocalTime.of(hourOfDay, minute);
            eventTimeStart.setText("Idő: " + time.getHour() + ":" + time.getMinute());
        }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
        timePickerDialog.show();
    }
    public void openDatePickerEnd(View view) {
        datePickerDialog = new DatePickerDialog(EventActivity.this, (view1, year, month, dayOfMonth) -> {
            LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            CalendarUtils.selectedDateEnd = selectedDate;
            eventDateEnd.setText("Dátum: " + "Dátum: " + CalendarUtils.selectedDateEnd.getYear() + "-" + CalendarUtils.selectedDateEnd.getMonthValue() + "-" + CalendarUtils.selectedDateEnd.getDayOfMonth());
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void openTimePickerEnd(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this, (view1, hourOfDay, minute) -> {
            timeEnd = LocalTime.of(hourOfDay, minute);
            eventTimeEnd.setText("Idő: " + timeEnd.getHour() + ":" + timeEnd.getMinute());
        }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
        timePickerDialog.show();
    }

    public void saveEvent(View view) {
        String eventName = eventNameET.getText().toString();
        Event event = new Event(eventName, CalendarUtils.selectedDate, time, CalendarUtils.selectedDateEnd, timeEnd);
        if(eventName.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Az esemény neve nem lehet üres!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }else if(event.getDateStart().compareTo(event.getDateEnd()) > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("A kezdő dátum nem lehet nagyobb, mint a befejező dátum!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }else if( event.getDateStart().compareTo(event.getDateEnd()) == 0 && event.getTimeStart().compareTo(event.getTimeEnd()) > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("A kezdő idő nem lehet nagyobb, mint a befejező idő!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }else if(event.getDateStart().compareTo(event.getDateEnd()) == 0 && event.getTimeStart().compareTo(event.getTimeEnd()) == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Az esemény kezdő és befejező ideje nem lehet azonos!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else{
            Event.events.add(event);
            finish();
        }
    }
}