
package com.lewa.droidtest.mms.sms;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.collect.Lists;
import com.lewa.droidtest.R;
import com.lewa.droidtest.Utils;

import java.util.ArrayList;

public class SmsSenderFragment extends Fragment {
    private Activity mActivity;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sms_sender_activity, null);
        mView.findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        return mView;
    }

    public void send() {
        String to = ((EditText) mView.findViewById(R.id.editTo)).getText().toString();
        String content = ((EditText) mView.findViewById(R.id.editContent)).getText().toString();
        sendSms(to, content);

    }

    private void sendSms(String destination, String content) {
        SmsManager manager = SmsManager.getDefault();

        Intent sentIntent = new Intent();
        sentIntent.setClass(mActivity, SentIntentReceiver.class);

        Intent deliveryIntent = new Intent();
        deliveryIntent.setClass(mActivity, DeliveryIntentReceiver.class);

        final ArrayList<PendingIntent> piSentIntent = Lists.newArrayList(PendingIntent
                .getBroadcast(mActivity, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        final ArrayList<PendingIntent> piDeliveryIntent = Lists.newArrayList(PendingIntent
                .getBroadcast(mActivity, 0, deliveryIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        ArrayList<String> smsDivs = manager.divideMessage(content);
        Utils.logE(this, "sending content #" + content + "# to #" + destination + "# with: "
                + manager);
        manager.sendMultipartTextMessage(destination, null, smsDivs, piSentIntent, piDeliveryIntent);

    }

}
