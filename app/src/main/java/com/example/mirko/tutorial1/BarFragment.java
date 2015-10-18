package com.example.mirko.tutorial1;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;


public class BarFragment extends Fragment {
    private static final Random RND = new Random();

    private volatile ProgressBar pb;

    private Runnable counterRunnable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View result = inflater.inflate(R.layout.fragment_bar, container, false);
        this.pb = (ProgressBar) result.findViewById(R.id.myProgressBar);
        final Button e = (Button) result.findViewById(R.id.myEditText);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SleepTask(BarFragment.this).execute();
            }
        });

        final Button delayedActivityButton = (Button) result.findViewById(R.id.myDelayButton);

        delayedActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), DelayedActivity.class);

                getActivity().startActivity(intent);
            }
        });

        final Button delayedActivityButtonOnButton = (Button) result.findViewById(R.id.myDelayButtonOnRandomView);

        this.counterRunnable = new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                delayedActivityButtonOnButton.setText(getString(R.string.activity_post_delay_button) +
                        String.format(" %d", count++));

                delayedActivityButtonOnButton.postDelayed(this, 1000);
            }
        };

        delayedActivityButtonOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delayedActivityButtonOnButton.postDelayed(BarFragment.this.counterRunnable, 1000);
            }
        });

        final Button buttonEventBus = (Button) result.findViewById(R.id.myRunEventBusActivity);
        buttonEventBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), EventBusActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return result;
    }

    private static class SleepTask extends AsyncTask<Void, Integer, Integer> {
        private final BarFragment f;

        public SleepTask(BarFragment f) {
            this.f = f;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(this.f.getActivity(), "On pre execute", Toast.LENGTH_SHORT)
                    .show();

            f.pb.setProgress(0);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            for (int i = 1; i <= 3; i++) {
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i * 100 / 3);
            }
            return RND.nextInt();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            f.pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Toast.makeText(this.f.getActivity(), "On post execute", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
