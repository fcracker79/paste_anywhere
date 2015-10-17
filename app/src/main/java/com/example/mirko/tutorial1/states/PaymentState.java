package com.example.mirko.tutorial1.states;

import com.example.mirko.tutorial1.BarFragment;

/**
 * Created by mirko on 17/10/15.
 */
public interface PaymentState {
    String HOST = "http://192.168.56.1:5000";

    void onEnter(PaymentState previousState, PaymentOwner owner);

    void onExit(PaymentState nextState, PaymentOwner owner);

    void onStartPayment(PaymentOwner owner);

    void nonceCreated(String nonce, PaymentOwner owner);
}
