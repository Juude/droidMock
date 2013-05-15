package com.lewa.droidtest.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lewa.droidtest.R;
import com.lewa.droidtest.Utils;

public class LinkedTextFragment extends Fragment {
    private static final String TAG = "LinkedTextFragment";
    private Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lined_text_fragment, null);
        
        /*
        LinearLayout layout  = new LinearLayout(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);
        
        LayoutParams textParams = new LayoutParams();
        textParams.width = Utils.dp(mActivity, 200);
        textParams.height = Utils.dp(mActivity, 100);
        
        LinedTextView text = new LinedTextView(mActivity);
        text.setText("nihao ma!!");
        text.setLayoutParams(textParams);
        
        layout.addView(text);
        return layout;
        
        */
    }
}
