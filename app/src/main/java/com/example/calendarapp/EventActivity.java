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
        eventDateStart.setText("Date: " + currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth());
        eventTimeStart.setText("Time: " + time.getHour() + ":" + time.getMinute());
        eventDateEnd.setText("Date: " + currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth());
        eventTimeEnd.setText("Time: " + time.getHour() + ":" + time.getMinute());

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
            eventDateStart.setText("Date: " + CalendarUtils.selectedDate.getYear() + "-" + CalendarUtils.selectedDate.getMonthValue() + "-" + CalendarUtils.selectedDate.getDayOfMonth());
            CalendarUtils.selectedDate = selectedDate;
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void openTimePickerStart(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this, (view1, hourOfDay, minute) -> {
            time = LocalTime.of(hourOfDay, minute);
            if(time.getMinute() < 10){
                eventTimeStart.setText("Time: " + time.getHour() + ":0" + time.getMinute());
            }else{
                eventTimeStart.setText("Time: " + time.getHour() + ":" + time.getMinute());
            }
        }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
        timePickerDialog.show();
    }
    public void openDatePickerEnd(View view) {
        datePickerDialog = new DatePickerDialog(EventActivity.this, (view1, year, month, dayOfMonth) -> {
            LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            CalendarUtils.selectedDateEnd = selectedDate;
            eventDateEnd.setText("Date: " + CalendarUtils.selectedDateEnd.getYear() + "-" + CalendarUtils.selectedDateEnd.getMonthValue() + "-" + CalendarUtils.selectedDateEnd.getDayOfMonth());
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void openTimePickerEnd(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this, (view1, hourOfDay, minute) -> {
            timeEnd = LocalTime.of(hourOfDay, minute);
            if(timeEnd.getMinute() < 10){
                eventTimeEnd.setText("Time: " + timeEnd.getHour() + ":0" + timeEnd.getMinute());
            }else{
                eventTimeEnd.setText("Time: " + timeEnd.getHour() + ":" + timeEnd.getMinute());
            }
        }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
        timePickerDialog.show();
    }

    public void saveEvent(View view) {
        String eventName = eventNameET.getText().toString();
        Event event = new Event(eventName, CalendarUtils.selectedDate, time, CalendarUtils.selectedDateEnd, timeEnd);
        if(eventName.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The event's name can't be empty!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }else if(event.getDateStart().compareTo(event.getDateEnd()) > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The starting date can't be bigger, then the ending date!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }else if( event.getDateStart().compareTo(event.getDateEnd()) == 0 && event.getTimeStart().compareTo(event.getTimeEnd()) > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The starting time can't be bigger, then the ending time!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }else if(event.getDateStart().compareTo(event.getDateEnd()) == 0 && event.getTimeStart().compareTo(event.getTimeEnd()) == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The event's starting and ending date and time can't be the same!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else{
            Event.events.add(event);
            finish();
        }
    }
}