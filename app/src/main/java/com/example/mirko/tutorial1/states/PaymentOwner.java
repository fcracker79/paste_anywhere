package com.example.mirko.tutorial1.states;

/**
 * Created by mirko on 17/10/15.
 */
public interface PaymentOwner {
    void setToken(String token);
    String getToken();
    void setState(PaymentState state);
    void startPaymentActivity();
    void setNonce(String nonce);
    String getNonce();
    void paymentSuccessful(String details);
    void disableFurtherClicks();
    void enableClick();
    String getCustomerId();
    void setCustomerId(String customerId);
}
