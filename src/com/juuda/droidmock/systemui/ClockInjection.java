package com.juuda.droidmock.systemui;

import android.content.Context;

import com.juuda.droidmock.R;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ClockInjection {
    public static final String TAG = "ClockInjection";
    
    public String getZhAmPmString(Context context, Calendar calendar){
        String format = context.getResources().getString(com.juuda.droidmock.R.string.twelve_hour_time_format_nopm);
        return getTimeSliceString(context, calendar.get(Calendar.HOUR_OF_DAY)) + new SimpleDateFormat(format).format(calendar.getTime());
    }   
  
    public String getTimeSliceString(Context context, int hour) {
        int timeId;
        if(hour < 4) {
            timeId = R.string.time_0_3;
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
    }
}
