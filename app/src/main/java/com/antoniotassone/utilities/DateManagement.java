package com.antoniotassone.utilities;

import org.json.JSONObject;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateManagement{
    private DateManagement(){}

    public static Calendar copyTimestamp(Calendar original){
        if(original == null){
            return new GregorianCalendar();
        }
        int year = original.get(Calendar.YEAR);
        int month = original.get(Calendar.MONTH) + 1;
        int day = original.get(Calendar.DAY_OF_MONTH);
        int hour = original.get(Calendar.HOUR_OF_DAY);
        int minute = original.get(Calendar.MINUTE);
        int second = original.get(Calendar.SECOND);
        int millisecond = original.get(Calendar.MILLISECOND);
        Calendar output = new GregorianCalendar();
        output.set(Calendar.YEAR,year);
        output.set(Calendar.DAY_OF_MONTH,day);
        output.set(Calendar.HOUR_OF_DAY,hour);
        output.set(Calendar.MINUTE,minute);
        output.set(Calendar.SECOND,second);
        output.set(Calendar.MILLISECOND,millisecond);
        switch(month){
            case 1:
                output.set(Calendar.MONTH,Calendar.JANUARY);
                break;
            case 2:
                output.set(Calendar.MONTH,Calendar.FEBRUARY);
                break;
            case 3:
                output.set(Calendar.MONTH,Calendar.MARCH);
                break;
            case 4:
                output.set(Calendar.MONTH,Calendar.APRIL);
                break;
            case 5:
                output.set(Calendar.MONTH,Calendar.MAY);
                break;
            case 6:
                output.set(Calendar.MONTH,Calendar.JUNE);
                break;
            case 7:
                output.set(Calendar.MONTH,Calendar.JULY);
                break;
            case 8:
                output.set(Calendar.MONTH,Calendar.AUGUST);
                break;
            case 9:
                output.set(Calendar.MONTH,Calendar.SEPTEMBER);
                break;
            case 10:
                output.set(Calendar.MONTH,Calendar.OCTOBER);
                break;
            case 11:
                output.set(Calendar.MONTH,Calendar.NOVEMBER);
                break;
            case 12:
                output.set(Calendar.MONTH,Calendar.DECEMBER);
                break;
        }
        return output;
    }

    public static String printTimestamp(Calendar original){
        if(original == null){
            original = new GregorianCalendar();
        }
        int year = original.get(Calendar.YEAR);
        int month = original.get(Calendar.MONTH) + 1;
        int day = original.get(Calendar.DAY_OF_MONTH);
        int hour = original.get(Calendar.HOUR_OF_DAY);
        int minute = original.get(Calendar.MINUTE);
        int second = original.get(Calendar.SECOND);
        int millisecond = original.get(Calendar.MILLISECOND);
        String output = "{";
        output += "\"year\":" + year + ",";
        output += "\"month\":" + month + ",";
        output += "\"day\":" + day + ",";
        output += "\"hour\":" + hour + ",";
        output += "\"minute\":" + minute + ",";
        output += "\"second\":" + second + ",";
        output += "\"millisecond\":" + millisecond;
        output += "}";
        return output;
    }

    public static Calendar createTimestamp(JSONObject object){
        if(object == null){
            return new GregorianCalendar();
        }
        int year = object.getInt("year");
        int month = object.getInt("month");
        int day = object.getInt("day");
        int hour = object.getInt("hour");
        int minute = object.getInt("minute");
        int second = object.getInt("second");
        int millisecond = object.getInt("millisecond");
        Calendar output = new GregorianCalendar();
        output.set(Calendar.YEAR,year);
        output.set(Calendar.DAY_OF_MONTH,day);
        output.set(Calendar.HOUR_OF_DAY,hour);
        output.set(Calendar.MINUTE,minute);
        output.set(Calendar.SECOND,second);
        output.set(Calendar.MILLISECOND,millisecond);
        switch(month){
            case 1:
                output.set(Calendar.MONTH,Calendar.JANUARY);
                break;
            case 2:
                output.set(Calendar.MONTH,Calendar.FEBRUARY);
                break;
            case 3:
                output.set(Calendar.MONTH,Calendar.MARCH);
                break;
            case 4:
                output.set(Calendar.MONTH,Calendar.APRIL);
                break;
            case 5:
                output.set(Calendar.MONTH,Calendar.MAY);
                break;
            case 6:
                output.set(Calendar.MONTH,Calendar.JUNE);
                break;
            case 7:
                output.set(Calendar.MONTH,Calendar.JULY);
                break;
            case 8:
                output.set(Calendar.MONTH,Calendar.AUGUST);
                break;
            case 9:
                output.set(Calendar.MONTH,Calendar.SEPTEMBER);
                break;
            case 10:
                output.set(Calendar.MONTH,Calendar.OCTOBER);
                break;
            case 11:
                output.set(Calendar.MONTH,Calendar.NOVEMBER);
                break;
            case 12:
                output.set(Calendar.MONTH,Calendar.DECEMBER);
                break;
        }
        return output;
    }
}