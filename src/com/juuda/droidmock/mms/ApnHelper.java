
package com.juuda.droidmock.mms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.text.TextUtils;

import com.android.internal.telephony.Phone;

import java.util.ArrayList;
import java.util.List;

public class ApnHelper {
    private Context mContext;

    public static class APN {
        public String MMSCenterUrl = "";
        public String MMSPort = "";
        public String MMSProxy = "";

        @Override
        public String toString()
        {
            return String.format("url: %s#port: %s#proxy: %s\n", MMSCenterUrl, MMSPort, MMSProxy);
        }
    }

    public ApnHelper(final Context context) {
        this.mContext = context;
    }

    public List<APN> getMMSApns() {
        final Cursor apnCursor = mContext.getContentResolver().query(
                Uri.withAppendedPath(Telephony.Carriers.CONTENT_URI, "current"), null, null, null,
                null);
        if (apnCursor == null) {
            return new ArrayList<APN>();
        }
        else {
            final List<APN> results = new ArrayList<APN>();
            if (apnCursor.moveToFirst()) {
                do {
                    final String type = apnCursor.getString(apnCursor
                            .getColumnIndex(Telephony.Carriers.TYPE));
                    if (!TextUtils.isEmpty(type)
                            && (type.equalsIgnoreCase(Phone.APN_TYPE_ALL) || type
                                    .equalsIgnoreCase(Phone.APN_TYPE_MMS))) {
                        final String mmsc = apnCursor.getString(apnCursor
                                .getColumnIndex(Telephony.Carriers.MMSC));
                        final String mmsProxy = apnCursor.getString(apnCursor
                                .getColumnIndex(Telephony.Carriers.MMSPROXY));
                        final String port = apnCursor.getString(apnCursor
                                .getColumnIndex(Telephony.Carriers.MMSPORT));
                        final APN apn = new APN();
                        apn.MMSCenterUrl = mmsc;
                        apn.MMSProxy = mmsProxy;
                        apn.MMSPort = port;
                        results.add(apn);
                    }
                }
                while (apnCursor.moveToNext());
            }
            apnCursor.close();
            return results;
        }
    }
}
