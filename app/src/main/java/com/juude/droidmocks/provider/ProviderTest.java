package com.juude.droidmocks.provider;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;

public class ProviderTest extends Mocker {

    protected static final String TAG = "ProviderTest";

    public ProviderTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        registerObserver();
    }
    
    class MyObserver extends ContentObserver {
        private boolean mIsNetMgr = false;
        public MyObserver(Handler handler) {
            super(handler);
        }
        public void setIsNetMgr(boolean isNetMgr) {
            mIsNetMgr = isNetMgr;
        }
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.e(TAG, "changed : "  + uri);
            if(mIsNetMgr) {
                queryNetMgr();
            }
            super.onChange(selfChange, uri);
        }
        
    }
    
    
    private void queryNetMgr() {
        Cursor cursor = mContext.getContentResolver().query(Uri.parse("content://com.lewa.netmgr/info"),
                new String[] {"limit", "used", "uids"},
                null,
                null,
                null);
        try {
            if(cursor != null && cursor.moveToFirst()) {
                for(int i = 0 ; i < cursor.getColumnCount(); i++) {
                    Log.e(TAG, cursor.getColumnName(i) + " : " + cursor.getString(i));
                }
            }
        }
        finally {
            cursor.close();
        }

    }
    
    public void registerObserver() {
        String uriString = MockUtils.getString(mExtras, "uri", "content://com.lewa.netmgr/info");
        MyObserver observer = new MyObserver(new Handler());
        if(uriString.equals("content://com.lewa.netmgr/info")) {
            observer.setIsNetMgr(true);
        }
        Uri uri = Uri.parse(uriString);
        mContext.getContentResolver().registerContentObserver(uri, 
                true, 
                observer
        );
        //mContext.getContentResolver().notifyChange(uri, null);
    }

}
