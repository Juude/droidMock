
package com.lewa.droidtest.adb;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lewa.droidtest.R;

public class TurnOffAdbFragment extends Fragment {
    private Activity mActivity;

    @Override
    public void onResume() {
        updateAdb();
        updateDisableStatusBar();
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.turn_off_adb_activity, null);

        Button toogleAdb = (Button) v.findViewById(R.id.toogleAdb);
        toogleAdb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toogleAdb();
            }
        });
        /*
         * Button buttonTurnoffUsb =
         * (Button)v.findViewById(R.id.buttonTurnoffUsb);
         * buttonTurnoffUsb.setOnClickListener(new View.OnClickListener() {
         * @Override public void onClick(View v) { turnOffUsb(); } }); Button
         * toggle = (Button)v.findViewById(R.id.buttonTurnoffUsb);
         * buttonTurnoffUsb.setOnClickListener(new View.OnClickListener() {
         * @Override public void onClick(View v) { turnOffUsb(); } });
         */
        return v;
    }

    private void updateAdb() {
        TextView textToogleAdb = (TextView) mActivity.findViewById(R.id.textToogleAdb);
        int adb = Settings.Secure.getInt(mActivity.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0);
        textToogleAdb.setText("" + adb);
    }

    public void toogleDown(View v) {
        int disableStatusBar = Settings.Secure.getInt(mActivity.getContentResolver(),
                "disableStatusBar", 0);
        disableStatusBar = 1 - disableStatusBar;
        Settings.Secure
                .putInt(mActivity.getContentResolver(), "disableStatusBar", disableStatusBar);
        updateDisableStatusBar();
    }

    private void updateDisableStatusBar() {
        TextView textToogleDown = (TextView) mActivity.findViewById(R.id.textToogleDown);
        int adb = Settings.Secure.getInt(mActivity.getContentResolver(), "disableStatusBar", 0);
        textToogleDown.setText("" + adb);
    }

    private void toogleAdb() {
        int adb = Settings.Secure.getInt(mActivity.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0);
        adb = adb == 0 ? 1 : 0;
        Settings.Secure.putInt(mActivity.getContentResolver(), Settings.Secure.ADB_ENABLED, adb);
        updateAdb();
    }

    public void statusBarSettings(View v) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.systemui",
                "com.android.systemui.statusbar.switchwidget.PhoneStatusbarSettings"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /*
     * private void turnOffUsb() { Intent intent = new Intent();
     * intent.setComponent(new ComponentName("com.android.systemui",
     * "com.android.systemui.usb.UsbModeSelection"));
     * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); intent =
     * intent.setAction("UsbModeSelection.action.CHARGE_ONLY");
     * startActivity(intent); }
     */
}
