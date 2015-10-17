package com.example.mirko.tutorial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MaintTutorial1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        final LinearLayout l = (LinearLayout) findViewById(R.id.fragmentContent);
        if (getFragmentManager().findFragmentByTag("ffffff") == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentContent, new BarFragment(), "ffffff")
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

}
