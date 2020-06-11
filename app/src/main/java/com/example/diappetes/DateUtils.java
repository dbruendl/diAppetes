package com.example.diappetes;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * Checks if a given date is on the same day, month and year as today
     * (instantiated by {@code new Date()})
     */
    public static boolean isToday(Date date) {
        Date todayWithoutTime = setStartOfDay(new Date());
        Date dateWithoutTime = setStartOfDay(date);

        return todayWithoutTime.compareTo(dateWithoutTime) == 0;
    }

    /**
     * Returns a new {@link Date} object with the milliseconds according to
     * {@link #getStartOfDayMillis(Date)}
     */
    public static Date setStartOfDay(Date date) {
        return new Date(getStartOfDayMillis(date));
    }

    /**
     * Uses {@link Calendar#setTime(Date)} to convert the given date, sets
     * {@link Calendar#HOUR_OF_DAY}, {@link Calendar#MINUTE}, {@link Calendar#SECOND} and
     * {@link Calendar#MILLISECOND} of the given {@link Date} to {@code 0} and returns
     * its timestamp according to {@link Calendar#getTimeInMillis()}.
     */
    public static long getStartOfDayMillis(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * Uses {@link Calendar#setTime(Date)} to convert the given date, sets
     * {@link Calendar#HOUR_OF_DAY}, {@link Calendar#MINUTE}, {@link Calendar#SECOND} and
     * {@link Calendar#MILLISECOND} of the given {@link Date} to their max values and returns
     * its timestamp according to {@link Calendar#getTimeInMillis()}.
     */
    public static long getEndOfDayMillis(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTimeInMillis();
    }
}
