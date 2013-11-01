package com.lewa.droidtest.test;

import android.content.Context;
import android.os.Bundle;

public abstract class Test {
    protected Context mContext;
    protected Bundle mExtras;
    public Test (Context context, Bundle extras) {
        mContext = context;
        mExtras = extras;
    }
    
    public void setArgs(Bundle extras) {
        mExtras = extras;
    }
    public abstract void test();
}
