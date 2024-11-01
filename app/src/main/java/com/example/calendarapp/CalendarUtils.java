package com.example.calendarapp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {
    public static LocalDate selectedDate;
    public static LocalDate selectedDateEnd;

    public static String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String monthDayFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }
    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static ArrayList<LocalDate> daysInMonthA() {
        ArrayList<LocalDate> daysInMonthA = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate prevMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();


        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 2; i < 44; i++) {
            if(i <= dayOfWeek){
                daysInMonthA.add(LocalDate.of(prevMonth.getYear(), prevMonth.getMonth(),prevDaysInMonth + i - dayOfWeek));
            }else if (i > daysInMonth + dayOfWeek){
                daysInMonthA.add(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(),i - dayOfWeek - daysInMonth));
            }else{
                daysInMonthA.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),i - dayOfWeek));
            }
        }
        return daysInMonthA;
    }

    public static ArrayList<LocalDate> daysInWeekA() {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = mondayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while(current.isBefore(endDate)){
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate mondayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while(current.isAfter(oneWeekAgo)){
            if(current.getDayOfWeek() == DayOfWeek.MONDAY){
                return current;
            }
            current = current.minusDays(1);
        }
        return null;
    }


}
