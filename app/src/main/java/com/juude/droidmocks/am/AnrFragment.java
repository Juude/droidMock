package com.juude.droidmocks.am;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AnrFragment extends Fragment{

    protected static final String TAG = "AnrFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button button = new Button(getActivity());
        button.setText("click me to invoke anr");
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                Log.e(TAG, "I'm clicked");
                ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                //cm.setMobileDataEnabled(true);
                for(int i = 0; i < 12; i++) {
                    Log.e(TAG, "count to " + i);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 
                ViewGroup.LayoutParams.WRAP_CONTENT
                ));
        return button;
    }

}
