
package com.lewa.droidtest.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MemoryCleanerView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    public long mFreeMemory = 30;
    public long mTotalMemory = 200;
    private TextView mMemoryText = null;
    private ImageView mClearImage = null;
    private ImageView mClearOutImage = null;
    private static String TAG = "MemoryCleanerViews";
    private Animation mRotateBack = null;
    private Animation mRotateForward = null;
    
    public MemoryCleanerView(Context context) {
        this(context, null, 0);
    }

    public MemoryCleanerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemoryCleanerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /*
        inflater.inflate(R.layout.memory_cleanerance, this);
        
        mMemoryText = (TextView) this.findViewById(R.id.memoryinfo);
        mClearImage = (ImageView) this.findViewById(R.id.memclearall);
        mClearImage.setOnClickListener(this);
        mClearOutImage = (ImageView) this.findViewById(R.id.memclearout);
        mRotateBack = AnimationUtils.loadAnimation(mContext, R.anim.recent_rotater_back);
        
        mRotateForward = AnimationUtils.loadAnimation(mContext, R.anim.recent_rotater_forward);
        */
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        System.currentTimeMillis();
    }

    public void cleanAll()
    {
        update();
    }

    public void setFreeMemory(long freeMemory)
    {
        mFreeMemory = freeMemory;
        update();
    }

    private void update()
    {
        long usedMemory = mTotalMemory - mFreeMemory;
        double raito = (usedMemory * 1.0 / mTotalMemory);
        int textColor = 0;
        int drawable = 0;
        int outDrawable = 0;
        if (raito > 0.8)
        {
            textColor = 0xFFF0443B;
            //drawable = R.drawable.clearance_red;
            //outDrawable = R.drawable.clearance_red_out;
        }
        else if (raito > 0.3)
        {
            textColor = 0xFFF79A29;
            //drawable = R.drawable.clearance_yellow;
            //outDrawable = R.drawable.clearance_yellow_out;
        }
        else
        {
            textColor = 0xFF5DAE28;
            //drawable = R.drawable.clearance_green;
            //outDrawable = R.drawable.clearance_green_out;
        }

        mMemoryText.setTextColor(textColor);
       // String memoryText = String.format(mContext.getString(R.string.recent_meminfo), usedMemory,
        //        mTotalMemory);

       // mMemoryText.setText(memoryText);
        mClearImage.setImageResource(drawable);
        mClearOutImage.setImageResource(outDrawable);
    }

    private void clearAll()
    {
        mClearOutImage.setVisibility(View.VISIBLE);
        mClearOutImage.startAnimation(mRotateBack);
        mRotateBack.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                update();
            }
        });
    }

    @Override
    public void onClick(View v) {
        clearAll();
    }
    
    public void onClearComplete(final long freeMemory)
    {   
        mRotateBack.cancel();
        mClearOutImage.setVisibility(View.VISIBLE);
        mClearOutImage.startAnimation(mRotateForward);
        mRotateForward.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               setFreeMemory(freeMemory);
            }
        });
    }

}
