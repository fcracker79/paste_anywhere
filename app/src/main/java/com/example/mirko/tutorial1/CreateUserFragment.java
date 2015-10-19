package com.example.mirko.tutorial1;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mirko.tutorial1.states.PaymentState;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class CreateUserFragment extends DialogFragment {
    private OnFragmentInteractionListener mListener;

    public void setListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public static CreateUserFragment instance() {
        return new CreateUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static void setButtonStatus(Button b, EditText ... texts) {
        boolean result = true;
        for (EditText t : texts) {
            if (t.getText() == null || t.getText().toString().trim().length() == 0) {
                result = false;
                break;
            }
        }

        b.setEnabled(result);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.create_customer));

        final View v = inflater.inflate(R.layout.fragment_create_user, container, false);

        final EditText firstName = (EditText) v.findViewById(R.id.firstNameValue);
        final EditText lastName = (EditText) v.findViewById(R.id.lastNameValue);
        final Button dialogButton = (Button) v.findViewById(R.id.createCustomerDialogButton);

        final TextWatcher w =
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        setButtonStatus(dialogButton, firstName, lastName);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };

        setButtonStatus(dialogButton, firstName, lastName);
        firstName.addTextChangedListener(w);
        lastName.addTextChangedListener(w);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("firstName", firstName.getText().toString());
                params.put("lastName", firstName.getText().toString());
                client.post(String.format("%s/customers", PaymentState.HOST), params,
                        new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e("TOKEN", "User creation FAILURE", throwable);
                        // throw new RuntimeException(throwable);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.i("TOKEN", String.format("Received token %s", responseString));
                        final Map<String, ?> result = new Gson().fromJson(responseString, Map.class);

                        mListener.onCustomerCreated((String) result.get("id"));

                    }
                });
                getDialog().dismiss();
            }
        });

        return v;
    }

    public interface OnFragmentInteractionListener {
        void onCustomerCreated(String customerId);
    }

}
