package com.shsoftnet.shcommon.utils.date;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateCalculateUtil {

    private static Calendar getCalendar(long timestampMills) throws Exception{
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);

        Calendar instance = Calendar.getInstance();
        if(timestampMills > 0L) {
            instance.setTimeInMillis(timestampMills);
        }
        return instance;
    }

    public static int GetMonthFirstDate(long timestampMills) throws Exception{
        Calendar instance = getCalendar(timestampMills);
        return instance.getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    public static int GetMonthLastDate(long timestampMills) throws Exception{
        Calendar instance = getCalendar(timestampMills);
        return instance.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static long GetAddDiffYear(long timestampMills, int diff) throws Exception {
        Calendar instance = getCalendar(timestampMills);
        instance.add(Calendar.YEAR, diff);
        return instance.getTimeInMillis();
    }

    public static long GetAddDiffMonth(long timestampMills, int diff) throws Exception {
        Calendar instance = getCalendar(timestampMills);
        instance.add(Calendar.MONTH, diff);
        return instance.getTimeInMillis();
    }

    public static long GetAddDiffWeek(long timestampMills, int diff) throws Exception {
        Calendar instance = getCalendar(timestampMills);
        instance.add(Calendar.WEEK_OF_MONTH, diff);
        return instance.getTimeInMillis();
    }

    public static long GetAddDiffDate(long timestampMills, int diff) throws Exception {
        Calendar instance = getCalendar(timestampMills);
        instance.add(Calendar.DATE, diff);
        return instance.getTimeInMillis();
    }

    public static long GetDiffDate(long target1, long target2) throws Exception {
        return Math.abs( (target2 - target1) / (1000L * 60 * 60 * 24));
    }

    public static long GetDDay(long target1, long target2) throws Exception {
        long res = Math.abs( (target2 - target1) / (1000L * 60 * 60 * 24));
        if(res == 0) return 0L;
        else{
            return res * ( (target2 > target1) ? -1 : 1 );
        }
    }


    public static int IsBeforeAfter(long target1, long target2) throws Exception {
        Calendar instance1 = getCalendar(target1);
        Calendar instance2 = getCalendar(target2);

        return instance1.compareTo(instance2);
    }

}
