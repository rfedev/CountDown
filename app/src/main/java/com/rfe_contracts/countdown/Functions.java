package com.rfe_contracts.countdown;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Functions {

    public Functions() {
    }


    public static Date getDateFromString(String dateString,String formatString){
        try {
            return new SimpleDateFormat(formatString).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String getStringFromDate(Date date, String formatString){
        SimpleDateFormat outFormat = new SimpleDateFormat(formatString);
        return outFormat.format(date);
    }

    public static String getStringFromTime(int hour, int minute){
        return String.format("%02d",hour) + " : " + String.format("%02d",minute);
    }

    public static String getStringFromTime(Date date){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //In 24h format
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d",hour) + " : " + String.format("%02d",minute);
    }

    //Static method for converting "yyyy-MM-dd HH:mm:ss" dates (eg how they'll be stored in the SQLite DB) to a regionalsed date string.
//    public static String formatDateTime(Context context, String timeToFormat) {
//
//        String finalDateTime = "";
//
//        SimpleDateFormat iso8601Format = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss");
//
//        Date date = null;
//        if (timeToFormat != null) {
//            try {
//                date = iso8601Format.parse(timeToFormat);
//            } catch (ParseException e) {
//                date = null;
//            }
//
//            if (date != null) {
//                long when = date.getTime();
//                int flags = 0;
//                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
//                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
//                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
//                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;
//
//                finalDateTime = android.text.format.DateUtils.formatDateTime(context, when + TimeZone.getDefault().getOffset(when), flags);
//            }
//        }
//        return finalDateTime;
//    }



    public static String getCounterString(Date eventDate, final boolean fullText, final int precision ){

        //precision can be between 1 - 6 inclusive. Normally use 3 eg Year, month, day or day, hour, minute

        Date currentDate =  Calendar.getInstance().getTime();
        long timeDifference = currentDate.getTime() - eventDate.getTime() ;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long yearsInMilli = new Double(daysInMilli * 365.2422).longValue(); //year is 365.2422 days
        long monthsInMilli = yearsInMilli / 12;
        boolean eventPassed;
        String returnString;


        if ((precision > 6) || (precision <1)) {
            return "";
        }

        eventPassed = ((timeDifference >= 0) ? true : false);
        timeDifference = Math.abs(timeDifference);


        long elapsedYears = timeDifference / yearsInMilli;
        timeDifference = timeDifference % yearsInMilli;

        long elapsedMonths = timeDifference / monthsInMilli;
        timeDifference = timeDifference % monthsInMilli;

        long elapsedDays = timeDifference / daysInMilli;
        timeDifference = timeDifference % daysInMilli;

        long elapsedHours = timeDifference / hoursInMilli;
        timeDifference = timeDifference % hoursInMilli;

        long elapsedMinutes = timeDifference / minutesInMilli;
        timeDifference = timeDifference % minutesInMilli;

        long elapsedSeconds = timeDifference / secondsInMilli;


        class OutString {
            int precisionSoFar;
            void OutString(){precisionSoFar = 0;}
            public String get(long elapsed, String metric){
                if ((precision > precisionSoFar) && (elapsed > 0)) {
                    precisionSoFar++;
                    if (!fullText) {
                        metric = metric.substring(0, 1) + ", ";
                    } else {
                        metric = metric.substring(0, 1).toUpperCase() + metric.substring(1);
                        metric = ((elapsed == 1) ? " " + metric + ", " : " " + metric + "s, ");
                    }
                    return String.valueOf(elapsed) + metric;

                } else {
                    return "";
                }
            }
        }


        OutString outString = new OutString(); //Do I need to use Functions.
        returnString = outString.get(elapsedYears, "Year") + outString.get(elapsedMonths, "Month") + outString.get(elapsedDays, "Day") +
                outString.get(elapsedHours, "hour") + outString.get(elapsedMinutes, "minute") + outString.get(elapsedSeconds, "second") ;

        return "Counter: " + returnString.substring(0, returnString.length() - 2); //remove the last ", "

//System.out.printf(
//        "%d days, %d hours, %d minutes, %d seconds%n",
//        elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }



}
