
package com.juuda.droidmock.mms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.juuda.droidmock.mock.Mocker;

public class MmsSenderTest extends Mocker{
    
    public MmsSenderTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        Intent mmsIntent = new Intent(mContext, MmsSenderService.class);
        mContext.startService(mmsIntent);  
    }
}
