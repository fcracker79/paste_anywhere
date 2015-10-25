package com.example.mirko.tutorial1;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class MyPrefFragment extends PreferenceFragment {
    public static MyPrefFragment newInstance() {
        MyPrefFragment fragment = new MyPrefFragment();
        return fragment;
    }

    public MyPrefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
