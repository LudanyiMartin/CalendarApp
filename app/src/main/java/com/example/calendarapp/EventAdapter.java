package com.example.calendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        }
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        if(event.getDateStart() == event.getDateEnd()){
            String eventTitle = event.getName() + ": " + event.getTimeStart().getHour() + ":" + event.getTimeStart().getMinute() + " - " + event.getTimeEnd().getHour() + ":" + event.getTimeEnd().getMinute();
            eventCellTV.setText(eventTitle);
        }else{
            String eventTitle = event.getName() + ": " + event.getDateStart() + " " + event.getTimeStart().getHour() + ":" + event.getTimeStart().getMinute() + " - " + event.getDateEnd() + " " + event.getTimeEnd().getHour() + ":" + event.getTimeEnd().getMinute() + " (Több napos)";
            eventCellTV.setText(eventTitle);
        }
        return convertView;
    }
}
