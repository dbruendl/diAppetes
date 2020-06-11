package com.example.diappetes;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * Checks if a given date is on the same day, month and year as today
     * (instantiated by {@code new Date()})
     */
    public static boolean isToday(Date date) {
        Date todayWithoutTime = cutTimeInformation(new Date());
        Date dateWithoutTime = cutTimeInformation(date);

        return todayWithoutTime.compareTo(dateWithoutTime) == 0;
    }

    /**
     * Cuts off time information from a date object
     */
    public static Date cutTimeInformation(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return new Date(cal.getTimeInMillis());
    }
}
