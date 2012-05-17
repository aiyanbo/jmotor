package jmotor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Component:Utility
 * Description:Date utilities
 * Date: 11-8-18
 *
 * @author Andy.Ai
 */
public class DateUtils {
    public static final String defaultDatePattern = "yyyy-MM-dd";
    public static final String millisecondsPattern = "ms";
    public static final String secondsPattern = "ss";
    public static final String minutesPattern = "mm";
    public static final String hoursPattern = "HH";
    public static final String dayOfMonthPattern = "dd";
    public static final String monthPattern = "MM";
    public static final String yearsPattern = "yyyy";

    private DateUtils() {
    }

    public static String formatDate(Date date) {
        return formatDate(date, defaultDatePattern);
    }

    public static String formatDate(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatDate(Number date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parseDate(String value) throws ParseException {
        return parseDate(value, defaultDatePattern);
    }

    public static Date parseDate(String value, String pattern) throws ParseException {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.parse(value);
    }

    public static int milliseconds(Date date) {
        return Integer.valueOf(formatDate(date, millisecondsPattern));
    }

    public static int seconds(Date date) {
        return Integer.valueOf(formatDate(date, secondsPattern));
    }

    public static int minutes(Date date) {
        return Integer.valueOf(formatDate(date, minutesPattern));
    }

    public static int hours(Date date) {
        return Integer.valueOf(formatDate(date, hoursPattern));
    }

    public static int month(Date date) {
        return Integer.valueOf(formatDate(date, monthPattern));
    }

    public static int dayOfMonth(Date date) {
        return Integer.valueOf(formatDate(date, dayOfMonthPattern));
    }

    public static int years(Date date) {
        return Integer.valueOf(formatDate(date, yearsPattern));
    }
}
