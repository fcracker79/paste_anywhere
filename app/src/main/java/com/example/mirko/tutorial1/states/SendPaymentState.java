package com.example.mirko.tutorial1.states;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mirko on 17/10/15.
 */
public class SendPaymentState implements PaymentState {
    private SendPaymentState() {}

    private static final PaymentState INSTANCE = new SendPaymentState();

    public static PaymentState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(PaymentState previousState, final PaymentOwner owner) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("nonce", owner.getNonce());
        params.put("amount", 10);
        client.post(String.format("%s/pay", HOST), params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        final StringBuilder sb = new StringBuilder();
                        for (byte b : responseBody) {
                            sb.append((char) b);
                        }
                        final String strJson = sb.toString();
                        final Map<String, ?> jsonObject = new Gson().fromJson(strJson, Map.class);
                        final String details = String.format(
                                "id: [%s], status: [%s], success: [%s]",
                                jsonObject.get("id"),
                                jsonObject.get("status"),
                                jsonObject.get("success")
                        );
                        owner.paymentSuccessful(details);
                        Log.i("PAYMENT", "Received the message " + strJson);
                        owner.setState(InitState.getInstance());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("PAYMENT", "Error sending payment", error);
                        owner.setState(InitState.getInstance());
                    }
                }
        );
    }

    @Override
    public void onExit(PaymentState nextState, PaymentOwner owner) {

    }

    @Override
    public void onStartPayment(PaymentOwner owner) {

    }

    @Override
    public void nonceCreated(String nonce, final PaymentOwner owner) {

    }
}
