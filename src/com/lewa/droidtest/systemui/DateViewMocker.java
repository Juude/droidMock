package com.lewa.droidtest.systemui;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import com.lewa.droidtest.R;
import com.lewa.droidtest.mock.Mocker;

import java.util.Calendar;
import java.util.Date;

import libcore.icu.LocaleData;
import java.util.Locale;

public class DateViewMocker extends Mocker{

    private static final String TAG = "DateViewTest";

    public DateViewMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        
        final Context context = mContext;
        Date now = new Date();
        CharSequence dow = DateFormat.format("EEEE", now);
        CharSequence date = DateFormat.getLongDateFormat(context).format(now);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        boolean is24 = DateFormat.is24HourFormat(context);
        String format = null;
        LocaleData d = LocaleData.get(context.getResources().getConfiguration().locale);
        String zhAmPm = "";
        Locale locale  = mContext.getResources().getConfiguration().locale;
        boolean isZhAmPm = !is24 && 
                              (Locale.CHINA.equals(locale) || Locale.TAIWAN.equals(locale));
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int amPm = mCalendar.get(Calendar.AM_PM);
        if(isZhAmPm) {
            zhAmPm = new ClockInjection().getTimeSliceString(mContext, hour); 
        }
        else if(!is24){
            zhAmPm = DateUtils.getAMPMString(amPm);
        }
        
        Log.e(TAG, context.getString(R.string.lewa_status_bar_date_formatter, dow, date, zhAmPm));
    }
    
}
