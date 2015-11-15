package com.example.mirko.tutorial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MaintTutorial1Activity extends AppCompatActivity {
    public static final String RESTORE_FROM_PREFERENCES_EXTRA_INTENT = "restoreFromPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        final boolean restoreFromPreferences =
                extras != null && extras.getBoolean(RESTORE_FROM_PREFERENCES_EXTRA_INTENT, false);
        if (getFragmentManager().findFragmentById(android.R.id.content)==null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, SharedClipboardFragment.newInstance(restoreFromPreferences))
                    .addToBackStack("settings")
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
