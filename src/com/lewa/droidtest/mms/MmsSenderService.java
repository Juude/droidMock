
package com.lewa.droidtest.mms;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.android.internal.telephony.Phone;
import com.android.internal.util.Preconditions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class MmsSenderService extends Service {

    private static String HDR_VALUE_ACCEPT_LANGUAGE = HTTP.UTF_8;;
    private static final String HDR_KEY_ACCEPT = "Accept";
    private static final String HDR_KEY_ACCEPT_LANGUAGE = "Accept-Language";
    private static final String HDR_VALUE_ACCEPT = "*/*, application/vnd.wap.mms-message, application/vnd.wap.sic";

    public static String mUrl = "http://mmsc.monternet.com";
    private static final String TAG = "MmsSenderService";

    public static String mProxy = "10.0.0.172";
    public static int mPort = 80;

    // private IntentFilter mFilter = new IntentFilter();
    private MmsInfo mMmsInfo;

    private final int MESSAGE_START_APN = 1000;
    private final int MESSAGE_SEND_MMS = 2000;

    private ConnectivityManager mConnectivityManager = null;

    private String mTempPath = Environment.getExternalStorageDirectory()
            .getPath() + File.separator + "0000.jpg";

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MESSAGE_START_APN:
                    Log.e(TAG, "MESSAGE_START_APN");
                    if (!isMmsApn()) {

                        startApn();
                    }

                    break;

                case MESSAGE_SEND_MMS:
                    Log.e(TAG, "MESSAGE_SEND_MMS");
                    if (mMmsInfo != null)
                        Log.e(TAG, "mms tasking");
                    new Thread(new MmsSenderThread()).start();
                    break;
            }
        }
    };

    private void initApn() {

        ApnHelper helper = new ApnHelper(this);
        List<ApnHelper.APN> apns = helper.getMMSApns();
        if (apns != null && apns.size() > 0) {
            final String url = apns.get(0).MMSCenterUrl;
            if (url != null) {
                mUrl = apns.get(0).MMSCenterUrl;
            }
            else {
                Log.e(TAG, "url is null");
            }
        }

    }

    public void sendMms()
    {
        if (!isMmsApn())
        {
            mHandler.sendEmptyMessage(MESSAGE_START_APN);
        }
        else
        {
            mHandler.sendEmptyMessage(MESSAGE_SEND_MMS);
        }
    }

    private boolean isMmsApn()
    {
        NetworkInfo info = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        String currentApn = info.getExtraInfo();
        Log.e(TAG, "isMmsApn:" + currentApn);
        if (currentApn == null)
        {
            return false;
        }
        if (currentApn.equals("cmwap") || currentApn.equals("3gwap") || currentApn.equals("uniwap"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    protected boolean startApn() {

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }

        int result = mConnectivityManager.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE,
                Phone.FEATURE_ENABLE_MMS);

        if (result != 0)
        {

            mHandler.sendEmptyMessageDelayed(MESSAGE_START_APN, 1000);
            Log.e(TAG, "result!= 0...wait");
            return false;
        }
        else
        {
            Log.e(TAG, "FEATURE_ENABLE_MMS succeed!! " + isMmsApn());
            mHandler.sendEmptyMessage(MESSAGE_SEND_MMS);
            return true;
        }
    }

    private class MmsSenderThread implements Runnable {

        @Override
        public void run() {
            send();
        }

    }

    private byte[] send() {
        byte[] pduBody = mMmsInfo.getMMSBytes();
        HttpClient client = null;
        byte[] body = null;
        try {
            Log.e(TAG, "staring send." + pduBody + "length:" + pduBody.length);
            HttpHost httpHost = new HttpHost(mProxy, mPort);
            HttpParams httpParams = new BasicHttpParams();
            httpParams.setParameter(ConnRouteParams.DEFAULT_PROXY, httpHost);
            HttpConnectionParams.setConnectionTimeout(httpParams, 120000);
            HttpConnectionParams.setSoTimeout(httpParams, 120000);
            client = new DefaultHttpClient(httpParams);

            ByteArrayEntity entity = new ByteArrayEntity(pduBody);
            entity.setContentType("application/vnd.wap.mms-message");

            HttpPost post = new HttpPost(mUrl);
            post.setEntity(entity);
            post.addHeader(HDR_KEY_ACCEPT, HDR_VALUE_ACCEPT);
            post.addHeader(HDR_KEY_ACCEPT_LANGUAGE, HDR_VALUE_ACCEPT_LANGUAGE);
            post.addHeader(
                    "user-agent",
                    "Mozilla/5.0(Linux;U;Android 2.1-update1;zh-cn;ZTE-C_N600/ZTE-C_N600V1.0.0B02;240*320;CTC/2.0)AppleWebkit/530.17(KHTML,like Gecko) Version/4.0 Mobile Safari/530.17");

            HttpParams params = client.getParams();
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            HttpResponse response = client.execute(post);

            StatusLine status = response.getStatusLine();

            if (status.getStatusCode() != 200) {
                throw new IOException("HTTP error: " + status.getReasonPhrase());
            }

            HttpEntity resentity = response.getEntity();
            Log.e(TAG, "response HttpEntity:" + resentity);
            if (resentity != null) {
                try {
                    if (resentity.getContentLength() > 0) {
                        body = new byte[(int) resentity.getContentLength()];
                        Log.e(TAG, "body length is : " + (int) resentity.getContentLength());
                        DataInputStream dis = new DataInputStream(
                                resentity.getContent());
                        try {
                            dis.readFully(body);
                        }
                        finally {
                            try {
                                dis.close();
                            }
                            catch (IOException e) {
                                Log.e(TAG, "Error closing input stream: "
                                        + e.getMessage());
                            }
                        }
                    }
                }
                finally {
                    if (entity != null) {
                        entity.consumeContent();
                    }
                }
            }
            Log.e(TAG, "result:" + new String(body));
            mMmsInfo = null;
            stopSelf();
            return body;
        }
        catch (IllegalStateException e) {
            Log.e(TAG, "", e);
        }
        catch (IllegalArgumentException e) {
            Log.e(TAG, "", e);
        }
        catch (SocketException e) {
            Log.e(TAG, "", e);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (body == null)
            {
                mHandler.sendEmptyMessageDelayed(MESSAGE_SEND_MMS, 10 * 60 * 1000);
            }
            // unregisterNetworkReceiver();
        }
        return new byte[0];
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
        {
            return super.onStartCommand(intent, flags, startId);
        }
        File f = new File(mTempPath);

        final String destination = "18721723484";
        Preconditions.checkNotNull(destination, "cannot be nulll");
        mMmsInfo = new MmsInfo(this, destination, "test message");
        mMmsInfo.addImagePart(f);
        initApn();
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        sendMms();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroying...");
        mHandler.removeMessages(MESSAGE_SEND_MMS);
        mHandler.removeMessages(MESSAGE_START_APN);
        // unregisterNetworkReceiver();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
