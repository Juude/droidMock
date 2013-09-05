
package com.lewa.droidtest.lock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lewa.droidtest.R;
import com.lewa.droidtest.Utils;

import java.lang.reflect.Method;

public class LockActivity extends Activity {
    private View mLockView = null;
    private WindowManager mWindowManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWindowManager = getWindowManager();
        setContentView(R.layout.lock_activity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeLockView();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        addLockView();
    }

    private void addLockView() {
        mLockView = new LockView(this);
        LayoutParams params;
        params = new LayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        params.type = LayoutParams.TYPE_KEYGUARD_DIALOG;
        params.flags =
                LayoutParams.FLAG_FORCE_NOT_FULLSCREEN |
                        LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
        addView(mLockView, params);
    }

    private void removeLockView() {
        Utils.logE(this, "removing....");
        removeView(mLockView);
    }

    @Override
    protected void onPause() {
        Utils.logE(this, "pause");
        super.onPause();
    }

    public class LockView extends LinearLayout {
        public static final String TAG = "LockView";
        private Activity mParent = null;
        private EditText mPasswordEdit;
        private PowerManager mPowerManager;
        private PowerManager.WakeLock mWakeLock;

        private static final int TIMEOUT = 1000;

        private static final int TIME_DELAY = 15000;

        public LockView(Activity context) {
            super(context);
            mParent = context;
            LayoutInflater inflater = (LayoutInflater) mParent.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.lock_view, this);
            mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                    PowerManager.ON_AFTER_RELEASE, "KEYGUARD");

            Button buttonOk = (Button) findViewById(R.id.buttonOk);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParent.finish();
                }
            });

            mPasswordEdit = null;
            mPasswordEdit.setFilters(new InputFilter[] {
                new InputFilter() {

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                            Spanned dest,
                            int dstart, int dend) {
                        requireWake();
                        return null;
                    }

                }
            });

            Button buttonKeyboard = (Button) findViewById(R.id.buttonShowKeyboard);
            buttonKeyboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) mParent
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.setFullscreenMode(true);
                    imm.showSoftInput(mPasswordEdit, 0);
                }
            });
        }

        private void requireWake()
        {
            Utils.logE(this, "requiredWake");
            mWakeLock.acquire();//
            mHandler.removeMessages(TIMEOUT);
            Message msg = mHandler.obtainMessage(TIMEOUT);
            mHandler.sendMessageDelayed(msg, TIME_DELAY);
        }

        private Handler mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TIMEOUT:
                        handleTimeout(msg.arg1);
                        return;
                }
            }
        };

        private void handleTimeout(int seq) {
            Utils.logE(this, "timed out");
            mWakeLock.release();
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            requireWake();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            requireWake();
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return super.onKeyDown(keyCode, event);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
        }
    }

    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    private void addView(View mLockView, LayoutParams params) {
        try {
            Class cls;
            cls = Class.forName("android.view.WindowManagerImpl");
            Method methodGetDefault = cls.getMethod("getDefault");
            Object obj = methodGetDefault.invoke(cls);
            Method methodAddView = cls.getMethod("addView", View.class,
                    ViewGroup.LayoutParams.class);
            methodAddView.invoke(obj, mLockView, params);
        }
        catch (Exception e) {
            mWindowManager.addView(mLockView, params);
        }
    }

    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    private void removeView(View mLockView) {
        try {
            Class cls;
            cls = Class.forName("android.view.WindowManagerImpl");
            Method methodGetDefault = cls.getMethod("getDefault");
            Object obj = methodGetDefault.invoke(cls);
            Method methodRemoveView = cls.getMethod("addView");
            methodRemoveView.invoke(obj, mLockView);
        }
        catch (Exception e) {
            mWindowManager.removeView(mLockView);
        }
    }

}
