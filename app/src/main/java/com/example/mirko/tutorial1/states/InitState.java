package com.example.mirko.tutorial1.states;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mirko on 17/10/15.
 */
public class InitState implements PaymentState {
    private InitState() {}

    private static final PaymentState INSTANCE = new InitState();

    public static PaymentState getInstance() {
        return INSTANCE;
    }
    @Override
    public void onEnter(PaymentState previousState, PaymentOwner owner) {
        owner.setToken(null);
        owner.setNonce(null);
        owner.enableClick();
    }

    @Override
    public void onExit(PaymentState nextState, PaymentOwner owner) {

    }

    public void onStartPayment(final PaymentOwner owner) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format("%s/generate_token", HOST), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TOKEN", "Token FAILURE", throwable);
                throw new RuntimeException(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("TOKEN", String.format("Received token %s", responseString));
                owner.setToken(responseString);
                owner.disableFurtherClicks();
                owner.setState(StartPaymentActivityState.getInstance());
            }
        });
    }

    @Override
    public void nonceCreated(String nonce, PaymentOwner owner) {

    }
}
