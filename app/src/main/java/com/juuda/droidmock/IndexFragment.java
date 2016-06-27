package com.juuda.droidmock;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.juuda.droidmock.mock.MockReceiver;
public class IndexFragment extends Fragment{
    protected static final String TAG = "IndexFragment";
    private ListView mListView;
    private Activity mActivity;
    private HashMap<String, HashMap<String, String>>  mShortCuts = new HashMap<String, HashMap<String, String>>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        HashMap<String, String> cmdWlan = new HashMap<String, String>();
        cmdWlan.put("a", "network");
        cmdWlan.put("wifi", "1");
        cmdWlan.put("method", "toggle");
        
        mShortCuts.put("TOGGLE WLAN STATE", cmdWlan);
        
        HashMap<String, String> cmdMobile = new HashMap<String, String>();
        cmdMobile.put("a", "network");
        cmdMobile.put("mobile", "1");
        cmdMobile.put("method", "toggle");

        mShortCuts.put("TOGGLE MOBILE STATE", cmdMobile);
        
        HashMap<String, String> testNetwork = new HashMap<String, String>();
        testNetwork.put("a", "network");
        mShortCuts.put("TEST NETWORK", testNetwork);
        
        HashMap<String, String> notification = new HashMap<String, String>();
        notification.put("a", "notification");
        mShortCuts.put("Send Notification", notification);
        
        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put("a", "settings");
        mShortCuts.put("toggleAdb", settings);
        
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView = new ListView(mActivity);
        mListView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        final List<String> items = new  ArrayList<String>();
        items.addAll(mShortCuts.keySet());
        mListView.setAdapter(new ArrayAdapter<String>(
                mActivity, android.R.layout.simple_list_item_1, items));
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                HashMap<String, String> args = mShortCuts.get(items.get(position));
                Log.e(TAG, "args : " + args);
                for(String key : args.keySet()) {
                    i.putExtra(key, args.get(key));
                }
                Log.e(TAG, "1");
                MockReceiver receiver = new MockReceiver();
                Log.e(TAG, "2");

                receiver.invoke(mActivity, i.getExtras(), args.get("a"));
            }
        });
        return mListView;        
    }
    
}
