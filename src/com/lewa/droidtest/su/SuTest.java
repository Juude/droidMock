
package com.lewa.droidtest.su;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SuTest {
    private static final String TAG = "SuTest";

    public void testSu(final String... command) {
        try {
            String log = "running..";
            for (String cmd : command) {
                log = log + " " + cmd;
            }
            Log.e(TAG, log);

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                Log.e(TAG, "result : " + line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
