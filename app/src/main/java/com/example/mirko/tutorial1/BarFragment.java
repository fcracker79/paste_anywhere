package com.example.mirko.tutorial1;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.example.mirko.tutorial1.states.InitState;
import com.example.mirko.tutorial1.states.PaymentOwner;
import com.example.mirko.tutorial1.states.PaymentState;


public class BarFragment extends Fragment implements PaymentOwner {
    private Button paymentButton;

    private volatile PaymentState state;
    private volatile String token;
    private volatile String nonce;
    private volatile String customerId;

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String getNonce() {
        return nonce;
    }

    private String getStateName(PaymentState state) {
        return state.getClass().getName().replaceAll(".*\\.", "");
    }
    @Override
    public void setState(PaymentState newState) {
        Log.i("STATES", String.format("Entering %s state", getStateName(newState)));
        PaymentState previousState = null;
        if (this.state != null) {
            Log.i("STATES", String.format("Leaving %s state", getStateName(this.state)));
            this.state.onExit(newState, this);
            previousState = this.state;
        }
        this.state = newState;
        newState.onEnter(previousState, this);

        Log.i("STATES", String.format("Entered %s state", getStateName(newState)));
    }

    @Override
    public void startPaymentActivity() {
        Intent intent = new Intent(getActivity(), BraintreePaymentActivity.class);
        intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN,
                getToken());
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == BraintreePaymentActivity.RESULT_OK) {
                String paymentMethodNonce = data.getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);

                this.state.nonceCreated(paymentMethodNonce, this);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        this.setState(InitState.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View result = inflater.inflate(R.layout.fragment_bar, container, false);
        this.paymentButton = (Button) result.findViewById(R.id.myEditText);
        this.paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarFragment.this.state.onStartPayment(BarFragment.this);
            }
        });

        return result;
    }

    @Override
    public void paymentSuccessful(String details) {
        Toast.makeText(
                getActivity(),
                String.format("Payment completed: %s!", details),
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void disableFurtherClicks() {
        if (this.paymentButton != null) {
            this.paymentButton.setEnabled(false);
        }
    }

    @Override
    public void enableClick() {
        if (this.paymentButton != null) {
            this.paymentButton.setEnabled(true);
        }
    }
}
