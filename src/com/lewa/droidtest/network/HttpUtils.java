package com.lewa.droidtest.network;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.lewa.droidtest.test.TestUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.DataInputStream;
import java.io.IOException;

public class HttpUtils {
    private static final String TAG = "HttpUtils";
    
    public static void requestUrl(final Context context, final String url) {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                requestUrlInner(context, url);
                return null;
            }
            
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public static void requestUrlInner(final Context context, final String url) {
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("jdsong", context);
        HttpUriRequest request = new HttpGet(url);
        try {
            httpClient.execute(request, 
                    new ResponseHandler<Void>(){
                        @Override
                        public Void handleResponse(HttpResponse response)
                                throws ClientProtocolException, IOException {
                            StatusLine status = response.getStatusLine();
                            TestUtils.Toast(context, TAG, "statusCode " + status.getStatusCode());
                            HttpEntity entity = response.getEntity();
                            byte[] body = null;
                            if (entity != null) {
                                try {
                                    if (entity.getContentLength() > 0) {
                                        body = new byte[(int) entity.getContentLength()];
                                        DataInputStream dis = new DataInputStream(entity.getContent());
                                        try {
                                            dis.readFully(body);
                                            Log.e(TAG, "nihao" + new String(body));
                                        } finally {
                                            try {
                                                dis.close();
                                            } catch (IOException e) {
                                                Log.e(TAG, "Unexpected IOException.", e);
                                            }
                                        }
                                    }
                                } finally {
                                    if (entity != null) {
                                        entity.consumeContent();
                                    }
                                }
                            }
                            return null;
                        }
            });
        }
        catch (IOException e) {
            TestUtils.Toast(context, TAG, "error" + Log.getStackTraceString(e));
        }
        finally {
            httpClient.close();
        }
    }
}
