package com.lewa.droidtest.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.lewa.droidtest.mock.MockUtils;
import com.lewa.droidtest.mock.Mocker;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class NotificationCloud extends Mocker{
    public final String HTTPCLOUDSTR = "http://api.beta.lewaos.com/push/block_list?";

    public NotificationCloud(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void test() {
        fetchBlockList();
    }

    private void fetchInner() {
        final String updateTime = MockUtils.getString(mExtras, "update_time", getLastGMTtime());
        String httpget = null;
        try {
            httpget = HTTPCLOUDSTR + "client=" +
                    HttpUtils.getBiClientId(mContext) + "&update_time=" + URLEncoder.encode(updateTime, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.e("TAG", "" ,e);
        }
        HttpUtils.HttpResponse res =  HttpUtils.httpGet(mContext, httpget);
        Log.e("TAG", "httpget :  : " + httpget + "expires : " + res.expires + "\nresponse: " + res.response);
        if(res.expires != -1) {
            Log.e("TAG", "expires time : " + new java.util.Date(res.expires));
            String json = res.response;
            try {
                JSONTokener jsonParser = new JSONTokener(json);
                JSONObject result = (JSONObject) jsonParser.nextValue();
                JSONArray pkgarrayjson = result.getJSONArray("result");
                for (int i=0; i<pkgarrayjson.length(); i++) {
                    Log.e("TAG", "pkgarrayjson :  : " + pkgarrayjson.getString(i));
                }
                setLastGMTtime();
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, 
                        new Intent(mContext,NotificationFetchReceiver.class), 0);
                AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, res.expires, pi);
            }catch (Exception ex) {
                getBlackListDefault();
            }
        }else {
            getBlackListDefault();
        }
    }
    
    private void getBlackListDefault() {        
    }

    public void setLastGMTtime() {
        long ltime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String localTime , rawTime;
        ltime = System.currentTimeMillis();
        localTime = sdf.format(ltime);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        rawTime = sdf.format(ltime);
        SharedPreferences mStatusbar_settings = null;
        if(mStatusbar_settings == null)
            mStatusbar_settings = mContext.getSharedPreferences("statusbar_settings", 0);
        mStatusbar_settings.edit().putString("lastcloudnotitime", rawTime).commit();
    }
    
    private String getLastGMTtime() {
        SharedPreferences mStatusbar_settings = null;
        if(mStatusbar_settings  == null) {
            mStatusbar_settings = mContext.getSharedPreferences("statusbar_settings", 0);
        }
        return mStatusbar_settings.getString("lastcloudnotitime", "");
    }

    private void fetchBlockList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                fetchInner();
                return null;
            }
        }.execute();
    }    
    
}
