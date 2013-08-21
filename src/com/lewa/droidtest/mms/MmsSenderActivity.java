
package com.lewa.droidtest.mms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lewa.droidtest.R;

public class MmsSenderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.mms_sender_activity);

        super.onCreate(savedInstanceState);
    }

    public void send(View v) {
        sendMms();
    }

    private void sendMms()
    {
        Intent mmsIntent = new Intent(this, MmsSenderService.class);
        startService(mmsIntent);
    }
}
