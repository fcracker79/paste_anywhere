package com.example.mirko.tutorial1;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.example.mirko.tutorial1.states.PaymentOwner;
import com.example.mirko.tutorial1.states.PaymentState;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MaintTutorial1Activity extends AppCompatActivity implements CreateUserFragment.OnFragmentInteractionListener {
    private static final String TAG_PAYMENT_FRAGMENT = "paymentFragment";
    private static final String TAG_CREATE_USER_FRAGMENT = "createUserFragment";
    private static final int INTENT_CHOOSE_PAYMENT = 11235;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        if (getFragmentManager().findFragmentByTag(TAG_PAYMENT_FRAGMENT) == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentContent, new BarFragment(), TAG_PAYMENT_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maint_tutorial1, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onCustomerCreated(String customerId) {
        Toast.makeText(this, String.format("Customer [%s] created", customerId), Toast.LENGTH_SHORT)
            .show();

        ((PaymentOwner) getFragmentManager().findFragmentByTag(TAG_PAYMENT_FRAGMENT)).setCustomerId(customerId);

        findViewById(R.id.createPaymentMethodButton).setEnabled(true);
    }

    public void onCreateCustomerButtonClicked(View createCustomerButton) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(TAG_CREATE_USER_FRAGMENT);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        CreateUserFragment newFragment = CreateUserFragment.instance();
        newFragment.setListener(this);
        newFragment.show(ft, TAG_CREATE_USER_FRAGMENT);
    }

    public void onCreatePaymentMethodButtonClicked(View createPaymentMethodButton) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("customer_id",
                ((PaymentOwner) getFragmentManager().findFragmentByTag(TAG_PAYMENT_FRAGMENT)).getCustomerId());

        client.post(String.format("%s/generate_token", PaymentState.HOST), params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TOKEN", "Token FAILURE", throwable);
                // throw new RuntimeException(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String token) {
                Intent intent = new Intent(MaintTutorial1Activity.this, BraintreePaymentActivity.class);
                intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN,
                        token);
                startActivityForResult(intent, INTENT_CHOOSE_PAYMENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CHOOSE_PAYMENT) {
            final String nonce = data.getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("nonce", nonce);

            final String customerId = ((PaymentOwner) getFragmentManager().findFragmentByTag(TAG_PAYMENT_FRAGMENT))
                    .getCustomerId();

            client.post(
                    String.format("%s/customers/%s/payment_methods", PaymentState.HOST, customerId),
                    new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("TOKEN", "Token FAILURE", throwable);
                    // throw new RuntimeException(throwable);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Toast.makeText(MaintTutorial1Activity.this,
                            String.format("Payment method creation successful ? %s",
                                    (String) new Gson().fromJson(responseString, Map.class).get("result")),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
