
package com.juuda.droidmock.mock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.ToastService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MockUtils {
    @Retention(RetentionPolicy.RUNTIME)
    static public @interface Mock {
    }
    
    public static String getString(Bundle bundle, String key, String def) {
        String result = bundle.getString(key);
        result = result == null ? def : result;
        return result;
    }
    
    public static int getInt(Bundle bundle, String key, int def) {
        String str = bundle.getString(key);
        int result = def;
        try {
            result = Integer.parseInt(str);
        }
        catch(Exception e) {
        }
        return result; 
    }
    
    public static void Toast(Context context, String TAG, String msg) {
        Log.e(TAG, msg);
        Intent i = new Intent(context, ToastService.class);
        i.putExtra("message", msg);
        context.startService(i);
    }

}
