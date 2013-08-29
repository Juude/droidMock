
package com.lewa.droidtest.resources;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lewa.droidtest.Utils;

public class ResourcesTest extends Fragment {
    protected static final String TAG = "DataFragment";
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(Utils.newLP(mActivity, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        final TextView textView = new TextView(mActivity);
        textView.setLayoutParams(Utils.newLP(mActivity, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        textView.setText("");

        Button button = new Button(mActivity);
        button.setLayoutParams(Utils.newLP(mActivity, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        button.setText("clickme");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View empty = getEmptyView();
                empty.setVisibility(View.VISIBLE);
                empty.setLayoutParams(Utils.newLP(mActivity, LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                layout.addView(empty);
            }
        });

        layout.addView(textView);
        layout.addView(button);

        return layout;
    }

    private View getEmptyView() {
        try {
            Context context = mActivity.createPackageContext("com.lewa.PIM",
                    Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            int layout = context.getResources().getIdentifier("dialpad_list_empty_text", "layout",
                    "com.lewa.PIM");
            LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(layout, null);
        }
        catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
