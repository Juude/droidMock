
package com.juude.droidmocks.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class SampleProvider extends ContentProvider {
    public static final String TAG = SampleProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        Log.e(TAG, "ProviderTest onCreate");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[] {
            "C1"
        });
        cursor.addRow(new String[] {
            "1"
        });
        cursor.addRow(new String[] {
            "2"
        });
        cursor.addRow(new String[] {
            "3"
        });
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
