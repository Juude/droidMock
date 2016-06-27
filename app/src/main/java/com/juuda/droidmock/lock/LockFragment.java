
package com.juuda.droidmock.lock;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;

public class LockFragment extends Fragment {
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(mActivity);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);

        addButton(layout, "show Dialog", "addDialogView");

        addButton(layout, "show topmost test activity", "showTopmostActivity");

        addButton(layout, "show lock activity", "showLockActivity");

        return layout;
    }

    private void addButton(ViewGroup layout, final String title, final String onClick) {
        Button button = new Button(mActivity);
        LayoutParams params = new LayoutParams();
        params.width = 200;
        params.height = 100;
        button.setLayoutParams(params);
        button.setText(title);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Method method = LockFragment.class.getMethod(onClick);
                    method.invoke(LockFragment.this);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        layout.addView(button);
    }

    public void showTopmostActivity() {
        Intent i = new Intent(mActivity, TopmostActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(i);
    }

    public void showLockActivity() {
        Intent i = new Intent(mActivity, LockActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(i);
    }

    public void addDialogView() {
        final RelativeLayout layout = new RelativeLayout(mActivity);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.GRAY);
            }
        });

        layout.setBackgroundColor(Color.RED);
        LayoutParams params;
        params = new LayoutParams();
        params.width = 200;
        params.height = 400;
        params.type = LayoutParams.TYPE_SYSTEM_DIALOG;
        // WindowManagerImpl.getDefault().addView(layout, params);
    }
}
