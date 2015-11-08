package com.example.mirko.tutorial1;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SandboxRepository {
    private static final String SERVER_NAME = "http://192.168.122.1:5000/sandbox";

    private final Context ctx;
    private final AsyncHttpClient client = new AsyncHttpClient();

    public SandboxRepository(Context ctx) {
        this.ctx = ctx;
    }

    public void getData(String uid, final SandboxDataReceiveEventListener l) {
        client.get(ctx, String.format("%s/%s", SERVER_NAME, uid), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    final String result = (String) response.get("result");
                    l.onDataReceived(result);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                l.onDataReceiveFailed(statusCode, throwable);
            }
        });
    }
    public void postData(String uid, CharSequence data, final SandboxDataSaveEventListener l) {
        final RequestParams p = new RequestParams();
        p.put("data", data.toString());
        client.put(ctx, String.format("%s/%s", SERVER_NAME, uid), p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                l.onDataSaved();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                l.onDataSaveFailed(statusCode, throwable);
            }
        });
    }

    public void create(CharSequence token, final SandboxCreationEventListener l) {
        final RequestParams p = new RequestParams();
        p.put("token", token);

        client.post(ctx, SERVER_NAME, p, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                l.onSandboxCreationFailed(statusCode, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                l.onSandboxCreationFailed(statusCode, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    final JSONObject data = (JSONObject) response.get("result");
                    final String uri = String.format("%s/%s", SERVER_NAME, data.get("url"));
                    final String uid = (String) data.get("uid");
                    l.onSandboxCreated(uri, uid);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        });
    }


    public interface SandboxCreationEventListener {
        void onSandboxCreationFailed(int statusCode, Throwable cause);
        void onSandboxCreated(String uri, String uid);
    }

    public interface SandboxDataSaveEventListener {
        void onDataSaveFailed(int statusCode, Throwable cause);
        void onDataSaved();
    }

    public interface SandboxDataReceiveEventListener {
        void onDataReceived(String data);
        void onDataReceiveFailed(int statusCode, Throwable cause);
    }
}
