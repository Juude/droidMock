package com.lewa.droidtest.screen;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.Toast;

import java.lang.reflect.Method;

public class ScreenSizeFragment extends Fragment {

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout  = new LinearLayout(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);
     
        addButton(layout, "show ScreenSize", "getScreenSize");        
        return layout;
    }
    
    private void addButton(ViewGroup layout, final String title, final String onClick) {
        Button button = new Button(getActivity());
        LayoutParams params = new LayoutParams();
        params.width = 200;
        params.height = 100;
        button.setLayoutParams(params);
        button.setText(title);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                try {
                    Method method = ScreenSizeFragment.class.getMethod(onClick);
                    method.invoke(ScreenSizeFragment.this);
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        layout.addView(button);
    }
    
    public void getScreenSize() {
       
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size =new Point();
        display.getSize(size);
        Toast.makeText(getActivity(), 
                "width : " + size.x  +  "height" + size.y + 
                "density: " + outMetrics.density + "densityDpi" +  outMetrics.densityDpi
                + "scaledDensity " + outMetrics.scaledDensity, 
                Toast.LENGTH_LONG)
                .show();
    }
}
