
package com.juuda.droidmock.display;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.juuda.droidmock.mock.Mocker;

/*alias display*/
public class DisplayMocker extends Mocker{

    public DisplayMocker(Context context, Bundle bundle) {
        super(context, bundle);
    }

    public void getScreenSize() {
        WindowManager manager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        Point realSize = new Point();
        display.getRealSize(realSize);
        boolean hasNavigationBar = false;
//        try {
//            //hasNavigationBar = WindowManagerGlobal.getWindowManagerService().hasNavigationBar();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//
        String info = "realwidth : " + realSize.x + "realHeight: " + realSize.y + 
                "width : " + size.x + "height" + size.y +
                "density: " + outMetrics.density + "densityDpi" + outMetrics.densityDpi
                + "scaledDensity " + outMetrics.scaledDensity + "\nhas NavigationBar : " + hasNavigationBar;
        
        Toast.makeText(mContext,
                info,
                Toast.LENGTH_LONG)
                .show();
        
        Log.e("ScreenTest", info);
    }
   
    @Override
    public void dump() {
        getScreenSize();
    }
}
