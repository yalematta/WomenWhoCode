package com.example.womenwhocode.womenwhocode.utils;

/**
 * Created by shehba.shahab on 10/24/15.
 */

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class Utilities {
    public static final String TIME_FORMAT = "h:mm a";
    public static final String DATE_FORMAT = "MMMM dd";
    private static final String ABBR_YEAR = "y";
    private static final String ABBR_WEEK = "w";
    private static final String ABBR_DAY = "d";
    private static final String ABBR_HOUR = "h";
    private static final String ABBR_MINUTE = "m";

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String parseFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(parseFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = getAbbreviatedTimeSpan(dateMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    private static String getAbbreviatedTimeSpan(long timeMillis) {
        long span = Math.max(System.currentTimeMillis() - timeMillis, 0);
        if (span >= DateUtils.YEAR_IN_MILLIS) {
            return (span / DateUtils.YEAR_IN_MILLIS) + ABBR_YEAR;
        }
        if (span >= DateUtils.WEEK_IN_MILLIS) {
            return (span / DateUtils.WEEK_IN_MILLIS) + ABBR_WEEK;
        }
        if (span >= DateUtils.DAY_IN_MILLIS) {
            return (span / DateUtils.DAY_IN_MILLIS) + ABBR_DAY;
        }
        if (span >= DateUtils.HOUR_IN_MILLIS) {
            return (span / DateUtils.HOUR_IN_MILLIS) + ABBR_HOUR;
        }
        if (span >= DateUtils.MINUTE_IN_MILLIS) {
            return (span / DateUtils.MINUTE_IN_MILLIS) + ABBR_MINUTE;
        } else return (span / DateUtils.SECOND_IN_MILLIS) + "s";
    }

    public static String dateTimeParser(long time, String format) {
        DateFormat niceDate = new SimpleDateFormat(format);
        return niceDate.format(time);
    }

    public static long setLocalDateTime(String rawJsonDate) {
        // 2015-12-15 03:00:00 UTC
        String parseFormat = "yyyy-MM-dd HH:mm:ss zzz";
        SimpleDateFormat sf = new SimpleDateFormat(parseFormat, Locale.ENGLISH);
        sf.setTimeZone(TimeZone.getDefault());

        long time = 0;
        try {
            time = sf.parse(rawJsonDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}