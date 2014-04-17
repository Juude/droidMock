package com.juuda.droidmock.network;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.DataInputStream;
import java.io.IOException;

public class HttpClientTest extends Mocker{

    protected static final String TAG = "HttpClientTest";

    public HttpClientTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        request();
    }
    
    public void request() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                requestInner();
                return null;
            }
        }.execute();
    }
    
    public void requestInner() {
        Time t = new Time();
        t.set(System.currentTimeMillis());
        if(t.year > 2013) {
            return;
        } 
        else {
            Log.e(TAG, "requesting" + t.year);
        }
        final String content = MockUtils.getString(mExtras, "content", "songjindetest");
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("jdsong", mContext);
        HttpUriRequest request = new HttpGet("http://admin.lewatek.com/flowlog/savelog?str=" + content);
        try {
            httpClient.execute(request, 
                    new ResponseHandler<Void>(){
                        @Override
                        public Void handleResponse(HttpResponse response)
                                throws ClientProtocolException, IOException {
                            StatusLine status = response.getStatusLine();
                            MockUtils.Toast(mContext, TAG, "statusCode " + status.getStatusCode());
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
            e.printStackTrace();
        }
        finally {
            httpClient.close();
        }
    }
}
