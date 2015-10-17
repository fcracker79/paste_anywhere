package com.example.mirko.tutorial1.states;

import android.content.Intent;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;

/**
 * Created by mirko on 17/10/15.
 */
public class StartPaymentActivityState implements PaymentState {

    private StartPaymentActivityState() {
    }

    private static final PaymentState INSTANCE = new StartPaymentActivityState();

    public static PaymentState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(PaymentState previousState, PaymentOwner owner) {
        owner.startPaymentActivity();
    }

    @Override
    public void onExit(PaymentState nextState, PaymentOwner owner) {

    }

    @Override
    public void onStartPayment(PaymentOwner owner) {

    }

    @Override
    public void nonceCreated(String nonce, PaymentOwner owner) {
        owner.setNonce(nonce);
        owner.setState(SendPaymentState.getInstance());
    }
}
