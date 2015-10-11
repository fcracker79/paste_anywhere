package com.example.mirko.tutorial1;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyButton.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyButton extends Fragment implements View.OnClickListener {
    private static final String ARG_LABEL1 = "label1";
    private static final String ARG_LABEL2 = "label2";

    private String label1;
    private String label2;

    private OnFragmentInteractionListener mListener;

    private ImageView image;

    public static MyButton newInstance(String label1, String label2) {
        MyButton fragment = new MyButton();
        Bundle args = new Bundle();
        args.putString(ARG_LABEL1, label1);
        args.putString(ARG_LABEL2, label2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyButton() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            label1 = getArguments().getString(ARG_LABEL1);
            label2 = getArguments().getString(ARG_LABEL2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_my_button, container, false);

        ((TextView) v.findViewById(R.id.fragmentLabel)).setText(label1);

        final Button b = (Button) v.findViewById(R.id.fragmentButton);
        b.setText(label2);
        b.setOnClickListener(this);

        this.image = (ImageView) v.findViewById(R.id.fragmentImage);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        this.image.setVisibility(
                this.image.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE
        );
        if (mListener != null) {
            mListener.onFragmentInteraction(null);
        }
    }
}
