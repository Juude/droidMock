
package com.lewa.droidtest.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class SmartScrollView extends ScrollView {

    private String TAG = "SmartScrollView";
    private View mChildView1;
    private View mChildView2;
    private View mSpaceView;

    public SmartScrollView(Context context) {
        this(context, null, 0);
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "layouting .." + getHeight());
        Log.e(TAG, "mChildView1 .." + mChildView1.getHeight());
        Log.e(TAG, "mChildView2 .." + mChildView2.getHeight());
        Log.e(TAG, "mSpaceView .." + mSpaceView.getHeight());
        int spaceHeight = getHeight() - mChildView1.getHeight() - mChildView2.getHeight();
        mSpaceView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, spaceHeight));

        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        // mChildView1 = findViewById(R.id.child1);
        // mChildView2 = findViewById(R.id.child2);
        // mSpaceView = findViewById(R.id.space);
        super.onFinishInflate();
    }

}
