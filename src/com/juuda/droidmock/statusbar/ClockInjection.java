package com.juuda.droidmock.statusbar;

import android.content.Context;

import com.juuda.droidmock.R;

import java.util.Calendar;
import java.util.Locale;

public class ClockInjection {
    public static final String TAG = "ClockInjection";
    
    private String getTimeSliceString(Context context, int hour) {
        Locale current = context.getResources().getConfiguration().locale;
        if(!Locale.CHINA.equals(current)) {
            return "";
        }
        int timeId;
        if(hour < 4) {
            timeId = com.juuda.droidmock.R.string.time_0_3;
        }
        else if(hour < 7) {
            timeId = R.string.time_4_6;
        }
        else if(hour < 10) {
            timeId = R.string.time_7_9;
        }
        else if(hour < 12) {
            timeId = R.string.time_10_11;
        }
        else if(hour < 13) {
            timeId = R.string.time_12;
        }
        else if(hour < 17) {
            timeId = R.string.time_13_16;
        }
        else if(hour < 19) {
            timeId = R.string.time_17_18;
        }
        else {
            timeId = R.string.time_19_23;
        }
        return context.getString(timeId);
    }
    
    public void test(Context context) {
        for(int hour = 0; hour < 24; hour++) {
            System.out.println("hour : " + hour + " slice : " + getTimeSliceString(context, hour));
        }
        int current = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        System.out.println("current : " +current + " slice : " + getTimeSliceString(context, current));
    }
}
