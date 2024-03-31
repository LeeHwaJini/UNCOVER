package com.shsoftnet.shcommon.utils.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {


    public static boolean isValidDateStr(String format, String dateStr) {
        boolean isOk = true;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.KOREA);
            dateFormat.setLenient(false);
            dateFormat.parse(dateStr);
        }catch (ParseException | IllegalArgumentException e) {
            isOk = false;
        }
        return isOk;
    }

    public static Date toTimeStamp(String format, String dateStr) {
        Timestamp res = null;
        try{
            if(format == null || "".equals(format)) {
                format = "yyyy.MM.dd";
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.KOREA);
            dateFormat.setLenient(false);
            res = new Timestamp(dateFormat.parse(dateStr).getTime());
        }catch (Exception e) {
            res = new Timestamp(new Date().getTime());
        }
        return res;
    }

    public static Long toTimeStampMills(String format, String dateStr) {
        Long res = null;
        try{
            if(format == null || "".equals(format)) {
                format = "yyyy.MM.dd";
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.KOREA);
            dateFormat.setLenient(false);
            res = dateFormat.parse(dateStr).getTime();
        }catch (Exception e) {
            res = new Date().getTime();
        }
        return res;
    }

    public static String toDateFormat(String format, long timeStampMills) {
        Date targetDate = null;

        if(format == null || "".equals(format)) {
            format = "yyyy.MM.dd HH:mm:ss";
        }
        if(timeStampMills >= 0L) {
            targetDate = new Date(timeStampMills);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.KOREA);
        return dateFormat.format(targetDate);
    }

    public static String toDateFormat(String format, Date targetDate) {
        if(targetDate == null) {
            targetDate = new Date();
        }
        return toDateFormat(format, targetDate.getTime());
    }

}
