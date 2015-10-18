package com.example.mirko.tutorial1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class DelayedActivity extends AppCompatActivity {
    private Runnable runnable;
    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed);

        final ProgressBar pb = (ProgressBar) findViewById(R.id.myProgressBar);
        this.rootView = findViewById(android.R.id.content);
        this.runnable = new Runnable() {
            @Override
            public void run() {
                pb.setProgress(pb.getProgress() >= 100 ? 0 : pb.getProgress() + 10);

                DelayedActivity.this.rootView.postDelayed(this, 1000);
            }
        };

        this.rootView.postDelayed(this.runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.rootView.removeCallbacks(this.runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.runnable.run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delayed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
