
package com.juuda.droidmock.data;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juuda.droidmock.Utils;

public class DataFragment extends Fragment {
    protected static final String TAG = "DataFragment";
    private Activity mActivity;
    private BroadcastReceiver mReceiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "got you!! data cleared");
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addDataScheme("package");
        filter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        // new IntentFilter(Intent.ACTION_SETTINGS_PACKAGE_DATA_CLEARED)
        mActivity.registerReceiver(mReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(Utils.newLP(mActivity, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        TextView textView = new TextView(mActivity);
        textView.setLayoutParams(Utils.newLP(mActivity, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textView.setText(getStringValue(mActivity, "value", "DEFAULT"));

        TextView textView2 = new TextView(mActivity);
        textView2.setLayoutParams(Utils.newLP(mActivity, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textView2.setText(getStringValue(mActivity, "value", "DEFAULT"));

        Button button = new Button(mActivity);
        button.setLayoutParams(Utils.newLP(mActivity, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        button.setText("clickme");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setStringValue(mActivity, "value", "SJD");
                mActivity.recreate();
            }
        });

        layout.addView(textView);
        layout.addView(textView2);
        layout.addView(button);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mActivity.unregisterReceiver(mReceiver);

        System.out.println("exiting...");
        super.onDestroy();
    }

    public static String getStringValue(Context context, String key, String def) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                .getString(key, def);
    }

    public static void setStringValue(Context context, String key, String value) {
        context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                .edit().putString(key, value)
                .commit();
    }

}
