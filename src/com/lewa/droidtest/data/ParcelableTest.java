package com.lewa.droidtest.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Arrays;

public class ParcelableTest implements Parcelable{
    private int mData;
    private boolean mBool;
    private static final String TAG = "ParcelableTest";

    public ParcelableTest(int data, boolean bool) {
        mData = data;
        mBool = bool;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{mData, ((Boolean)mBool).hashCode()});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
        byte b = mBool == true ? (byte)1 : (byte)0; 
        dest.writeByte(b);
    }
    
    public static final Parcelable.Creator<ParcelableTest> CREATOR
       = new Parcelable.Creator<ParcelableTest>() {
        public ParcelableTest createFromParcel(Parcel in) {
            return new ParcelableTest(in);
        }
    
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };
    
    public ParcelableTest(Parcel in) {
        mData = in.readInt();
        byte b = in.readByte();
        mBool = (b == (byte)1);
    }
    
    @Override
    public String toString() {
        return "mData: " + mData + "#mBool: " + mBool; 
    }
    
    public static void test() {
        testBundle();
        testParcel();
    }
    
    public static void testBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", new ParcelableTest(100, true));
        ParcelableTest p = bundle.getParcelable("key");
        System.out.println(p);
    }
    
    public static void testParcel() {
        int writeInt = 88;
        int readInt = 0;
        //test int
        Parcel p = Parcel.obtain();
        p.writeInt(writeInt);
        p.setDataPosition(0);
        readInt = p.readInt();
        assertEquals(writeInt, readInt);
        p.recycle();
        
        //test Parcelable array
        Parcel pt = Parcel.obtain();
        ParcelableTest[] writeArray = new ParcelableTest[] {
            new ParcelableTest(100, true),
            new ParcelableTest(101, true)
        };
        pt.writeParcelableArray(writeArray, 0);
        pt.setDataPosition(0);
        Parcelable[] readArray =  (Parcelable[])pt.readParcelableArray(ParcelableTest.class.getClassLoader());
        
        for(Parcelable pp : readArray) {
            Log.d(TAG, "array@" + (ParcelableTest)pp);
        }
        pt.recycle();
    }
    
    public static void assertEquals(int expected, int actual) {
        int level = Log.ASSERT;
        if(expected == actual) {
            level = Log.DEBUG;
        }
        Log.println(level, TAG, "expected" + expected + "and actually is " + actual);
    }
}
