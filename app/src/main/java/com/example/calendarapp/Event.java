package com.example.calendarapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<Event> eventsForDate(LocalDate date){
        ArrayList<Event> eventsList = new ArrayList<>();

        for(Event event: events){
            if(event.getDateStart().equals(date) || event.getDateEnd().equals(date)){
                eventsList.add(event);
            }
        }

        return eventsList;
    }
    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> eventsList = new ArrayList<>();

        for (Event event : events) {
            if ((event.getDateStart().isBefore(date) || event.getDateStart().isEqual(date)) &&
                    (event.getDateEnd().isAfter(date) || event.getDateEnd().isEqual(date)) &&
                    (event.getTimeStart().getHour() <= time.getHour() || event.getDateStart().isBefore(date)) &&
                    (event.getTimeEnd().getHour() >= time.getHour() || event.getDateEnd().isAfter(date))) {
                eventsList.add(event);
            }
        }

        return eventsList;
    }
    private String name;
    private LocalDate dateStart;
    private LocalTime timeStart;
    private LocalDate dateEnd;
    private LocalTime timeEnd;

    public Event(String name, LocalDate dateStart, LocalTime timeStart, LocalDate dateEnd, LocalTime timeEnd) {
        this.name = name;
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static void setEvents(ArrayList<Event> events) {
        Event.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }
}
