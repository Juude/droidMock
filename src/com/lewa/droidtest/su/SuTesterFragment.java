package com.lewa.droidtest.su;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lewa.droidtest.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SuTesterFragment extends Fragment{
    private static final String TAG = "SuTesterFragment";
    private Button mButton = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout  = new LinearLayout(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);
        Utils.addButton(this, layout, "su echo", "testSu", new Class<?>[]{ String[].class}, new Object[]{new String[]{"su", "-c" ,"echo ddd >>1.txt"}});
        Utils.addButton(this, layout, "su -v", "testSuReboot");
        return layout;
    }
    
    /*
    public void testSu(final String... command) {
        try {
            String log = "running.." ;
            for(String cmd : command) {
                log = log + " " + cmd;
            }
            Log.e(TAG, log);
            
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(  
                    new InputStreamReader(process.getInputStream()));  
            String line = null;  
            while ((line = in.readLine()) != null) {  
                mButton.setText(mButton.getText() + "\n" +  line);
            }  
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    public void testSu(final String... command) {
        Intent i = new Intent("com.lewa.qrcode.SCAN");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
