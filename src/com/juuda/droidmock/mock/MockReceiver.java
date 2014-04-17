package com.juuda.droidmock.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.mock.MockUtils.Mock;



public class MockReceiver extends BroadcastReceiver{
    private static final String TAG = "TestReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            final Bundle bundle = intent.getExtras();
            final String action = MockUtils.getString(bundle, "a", "ACTION!!");
            Log.d(TAG, "executing..." + action + "in " + context.getPackageName());
            invoke(context, bundle, action);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void invoke(Context context, Bundle bundle, String action) {
        final String module = action;
        Class<? extends Mocker> clazz = Mocks.sModuleMap.get(module);    
        try {
            Constructor<? extends Mocker> mockerConstructor = clazz.getConstructor(Context.class, Bundle.class);
            Mocker mocker = (Mocker) mockerConstructor.newInstance(context, bundle);
            mocker.dump();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
    public void invoke(Context context, Intent intent) {
        Log.e(TAG, "xx" );
        try {
            final String action = MockUtils.getString(intent.getExtras(), "a", "ACTION!!");
            Log.e(TAG, "com.lewa.droidtest executing..." + action);
            Method actionMethod =  getClass().getMethod(action, Context.class, Intent.class);
            if(actionMethod.getAnnotation(MockUtils.Mock.class) != null) {
                actionMethod.invoke(this, context,intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    @Mock
    public void list(Context context, Intent intent) {
        Method[] methods = getClass().getMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(Mock.class)) {
                Log.e(TAG, ": " + method.getName());
            }
        }
    }
    
    
}
