package com.lewa.droidtest.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.lewa.droidtest.mock.Mocker;
import com.lewa.droidtest.mock.MockUtils;

public class NetworkTest extends Mocker{

    private static final String TAG = NetworkTest.class.getSimpleName();

    public NetworkTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void test() {
        try {
            NetworkTest.class.getMethod(MockUtils.getString(mExtras, "method", "profile"))
            .invoke(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void profile() {
        
        WifiManager wm = (WifiManager)mContext
                .getSystemService(Context.WIFI_SERVICE);
            
        ConnectivityManager cm = (ConnectivityManager)mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        Log.e(TAG, "connnected? : " + dumpInfo(info) + "\n" + 
                    "getMobileDataEnabled : " + cm.getMobileDataEnabled() + "\n" + 
                    "getWifiEnabled : " + (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED));
        HttpUtils.requestUrl(mContext, "http://www.baidu.com");
    }
    
    private String dumpInfo(NetworkInfo info) {
        if(info == null) {
            return null;
        }
        else {
            return "" + (info.getState() ==  NetworkInfo.State.CONNECTED);
        }
    }
    
    public void toggle() {
        if(mExtras.containsKey("mobile")) {
            ConnectivityManager cm = (ConnectivityManager)mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            cm.setMobileDataEnabled(!cm.getMobileDataEnabled());
        }
        else if (mExtras.containsKey("wifi")){
            WifiManager cm = (WifiManager)mContext
                    .getSystemService(Context.WIFI_SERVICE);
              cm.setWifiEnabled(cm.getWifiState() != WifiManager.WIFI_STATE_ENABLED);
        }
    }
    
}
