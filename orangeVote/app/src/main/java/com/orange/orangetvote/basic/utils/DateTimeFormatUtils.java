package com.orange.orangetvote.basic.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class DateTimeFormatUtils {

    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN);

    private static final SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.TAIWAN);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);


    public static String getCurrentTimestamp() {
        return datetimeFormat.format(new Date());
    }

    public static String datetimeFormat(Date date) {
        if (date == null) {
            return "";
        }

        return datetimeFormat.format(date);
    }

    public static String minuteFormat(Date date) {
        if (date == null) {
            return "";
        }

        return minuteFormat.format(date);
    }

    public static String dateFormat(Date date) {
        if (date == null) {
            return "";
        }

        return dateFormat.format(date);
    }

    public static String numberFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-", Locale.TAIWAN);

        if (date == null) {
            return "";
        }

        return sdf.format(date);
    }

    public static String shortFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d hh:mm", Locale.TAIWAN);

        if (date == null) {
            return "";
        }

        return sdf.format(date);
    }

    public static Date parseDate(String dateString) {
        try {
            return datetimeFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date afterHours(Date date, int hours) {
        if (date == null) {
            date = new Date();
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        ZonedDateTime zdt = localDateTime.plusHours(hours).atZone(ZoneId.systemDefault());

        return Date.from(zdt.toInstant());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isCrossDay(Date date) {
        if (date == null) {
            date = new Date();
        }

        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        LocalDateTime currentTemporal = toLocalDateTime(current);
        LocalDateTime calendarTemporal = toLocalDateTime(calendar);

        Duration duration = Duration.between(currentTemporal, calendarTemporal);

        return duration.toDays() >= 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }

        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean beforeOrEqual(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        LocalDateTime dateTime1 = dateToLocalDateTime(d1);
        LocalDateTime dateTime2 = dateToLocalDateTime(d2);

        return dateTime1.isBefore(dateTime2) || dateTime1.isEqual(dateTime2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean currentIsBefore(Date date) {
        if (date == null) {
            return false;
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return LocalDateTime.now().isBefore(localDateTime);
    }

}
