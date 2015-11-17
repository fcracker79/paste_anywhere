package com.example.mirko.tutorial1.backend;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

class StandaloneSandboxRepository implements SandboxRepository {
    private static final String SERVER_NAME = "http://192.168.122.1:5000";

    private final Context ctx;
    private final AsyncHttpClient client = new AsyncHttpClient();

    public StandaloneSandboxRepository(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void getData(String uid, String token, final SandboxRepository.SandboxDataReceiveEventListener l) {
        client.get(ctx, String.format("%s/sandbox/%s", SERVER_NAME, token), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    final JSONObject result = (JSONObject) response.get("result");
                    final String data = (String) result.get("content");
                    final String uri = String.format("%s/%s", SERVER_NAME, result.get("url"));

                    l.onDataReceived(data, uri);
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

    @Override
    public void postData(String uid, CharSequence data, final SandboxRepository.SandboxDataSaveEventListener l) {
        final RequestParams p = new RequestParams();
        p.put("data", data.toString());
        client.put(ctx, String.format("%s/sandbox/%s", SERVER_NAME, uid), p, new JsonHttpResponseHandler() {
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

    @Override
    public void create(CharSequence token, final SandboxRepository.SandboxCreationEventListener l) {
        final RequestParams p = new RequestParams();
        p.put("token", token);

        client.post(ctx, String.format("%s/sandbox", SERVER_NAME), p, new JsonHttpResponseHandler() {

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
}
