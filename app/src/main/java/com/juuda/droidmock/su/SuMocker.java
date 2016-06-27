
package com.juuda.droidmock.su;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.mock.Mocker;

import java.io.DataOutputStream;
import java.io.IOException;

public class SuMocker extends Mocker{
    public SuMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    private static final String TAG = "SuTest";
    
    @Override
    public void dump() {        
        final String cmd = "echo hello";
        // with root permission
        Process sh;
        try {
            sh = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(sh.getOutputStream());
            Log.d(TAG, cmd);
            os.writeBytes(cmd);
            os.close();
        }
        catch (IOException e) {
            Log.e(TAG, "", e);
        }      
    }

}
