package com.example.mirko.tutorial1;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;


public class ShowUserDetailsFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "customer";

    private CustomerDetail customer;

    public static ShowUserDetailsFragment newInstance(CustomerDetail customer) {
        ShowUserDetailsFragment fragment = new ShowUserDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, customer);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowUserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.show_customer));
        final View v = inflater.inflate(R.layout.fragment_show_user_details, container, false);

        ((TextView) v.findViewById(R.id.firstNameDetailsValue)).setText(customer.getFirstName());
        ((TextView) v.findViewById(R.id.lastNameDetailsValue)).setText(customer.getLastName());

        final CustomerAdapter adapter = new CustomerAdapter(getActivity(), customer.getCreditCards());

        final GridView lv = (GridView) v.findViewById(R.id.credit_cards);
        lv.setAdapter(adapter);

        return v;
    }

    private static class CustomerAdapter extends BaseAdapter {
        private final List<CustomerDetail.CreditCard> creditCards;
        private final Context context;
        public CustomerAdapter(Context context, List<CustomerDetail.CreditCard> creditCards) {
            this.creditCards = creditCards == null ?
                    Collections.<CustomerDetail.CreditCard>emptyList() :
                    creditCards;
            this.context = context;
        }

        @Override
        public int getCount() {
            return creditCards.size() * 5;
        }

        @Override
        public Object getItem(int position) {
            return null; // boh
        }

        @Override
        public long getItemId(int position) {
            final CustomerDetail.CreditCard creditCard = creditCards.get(position / 5);

            return new BigInteger(creditCard.getToken().getBytes()).longValue() << 4 + (position % 5);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int elementPosition = position / 5;
            final CustomerDetail.CreditCard creditCard = creditCards.get(elementPosition);
            final int fieldPosition = position % 5;

            final View v;
            switch(fieldPosition) {
                case 0:
                    TextView tv = new TextView(context, null);
                    tv.setText(Boolean.toString(creditCard.isDefaultCard()));
                    v = tv;
                    break;
                case 1:
                    tv = new TextView(context, null);
                    tv.setText(creditCard.getCardType());
                    v = tv;
                    break;
                case 2:
                    tv = new TextView(context, null);
                    tv.setText(creditCard.getExpiration());
                    v = tv;
                    break;
                case 3:
                    tv = new TextView(context, null);
                    tv.setText(creditCard.getToken());
                    v = tv;
                    break;
                case 4:
                    ImageView iv = new ImageView(context, null);
                    iv.setImageURI(Uri.parse(creditCard.getImageUrl()));
                    iv.setVisibility(View.VISIBLE);
                    v = iv;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return v;
        }
    }
}
