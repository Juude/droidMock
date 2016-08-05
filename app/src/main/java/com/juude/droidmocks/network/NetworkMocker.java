package com.juude.droidmocks.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;

public class NetworkMocker extends Mocker {

    private static final String TAG = NetworkMocker.class.getSimpleName();

    public NetworkMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        try {
            NetworkMocker.class.getMethod(MockUtils.getString(mExtras, "method", "profile"))
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
//        Log.e(TAG, "connnected? : " + dumpInfo(info) + "\n" +
//                    "getMobileDataEnabled : " + cm.getMobileDataEnabled() + "\n" +
//                    "getWifiEnabled : " + (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED));
//        HttpUtils.requestUrl(mContext, "http://www.baidu.com");
    }
    
    private String dumpInfo(NetworkInfo info) {
        //info.
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
            //cm.setMobileDataEnabled(!cm.getMobileDataEnabled());
        }
        else if (mExtras.containsKey("wifi")){
            WifiManager cm = (WifiManager)mContext
                    .getSystemService(Context.WIFI_SERVICE);
              cm.setWifiEnabled(cm.getWifiState() != WifiManager.WIFI_STATE_ENABLED);
        }
    }
    
}
