package com.lewa.droidtest.statusbar;

import android.view.View;

public class TransparentManager {
    private View mView;
    public TransparentManager(View v) {
        mView = v;
    } 
    
    enum State {
        
        BACKGROUND_BLACK_LEVEL(0), 
        BACKGROUND_TRANSLUCENT_LEVEL(1),
        BACKGROUND_TRANSPARENT_LEVEL(2),
        BACKGROUND_KEYGUARD_LEVEL(3);
        
        public int mState;
        State(int state) {
            mState = state;
        }
    }
    
    public void setState(State state) {
        
    }
}
