
package com.lewa.droidtest.screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import android.widget.Toast;
public class ScreenTest {
    public Context mContext;
    public Bundle mExtras;
    
    public ScreenTest(Context context, Intent intent) {
        mContext = context;
        mExtras = intent.getExtras();
    }

    public void getScreenSize() {
        WindowManager manager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        String info = "width : " + size.x + "height" + size.y +
                "density: " + outMetrics.density + "densityDpi" + outMetrics.densityDpi
                + "scaledDensity " + outMetrics.scaledDensity;
        
        Toast.makeText(mContext,
                info,
                Toast.LENGTH_LONG)
                .show();
        
        Log.e("ScreenTest", info);
    }
    
    public void test() {
        getScreenSize();
    }
}
