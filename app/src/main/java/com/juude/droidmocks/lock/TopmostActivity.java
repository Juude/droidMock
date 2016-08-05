
package com.juude.droidmocks.lock;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class TopmostActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
    }
}
