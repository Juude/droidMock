package com.lewa.droidtest.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.lewa.droidtest.test.Test;

public class NetworkTest extends Test{

    private static final String TAG = "NetworkTest";

    public NetworkTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void test() {
        profile();
    }
    
    public void profile() {
        
        WifiManager wm = (WifiManager)mContext
                .getSystemService(Context.WIFI_SERVICE);
            
        ConnectivityManager cm = (ConnectivityManager)mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        Log.e(TAG, "connnected? : " + (info.getState() ==  NetworkInfo.State.CONNECTED) + "\n" + 
                    "getMobileDataEnabled : " + cm.getMobileDataEnabled() + "\n" + 
                    "setWifiEnabled" + (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED));
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
