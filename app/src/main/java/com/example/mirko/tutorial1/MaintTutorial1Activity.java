package com.example.mirko.tutorial1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MaintTutorial1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        InputStream is = null;
        try {
            is = getResources().getAssets().open("mirko/test_resources.txt");
            final BufferedReader r = new BufferedReader(new InputStreamReader(is));
            final StringBuilder sb = new StringBuilder();

            for (String s = r.readLine(); s != null; s = r.readLine()) {
                sb.append(String.format("\n%s", s));
            }

            final String localStorage =
                    String.format(
                            "\nfilesDir: %s\ncacheDir: %s",
                            getFilesDir(),
                            getCacheDir());
            sb.append(String.format("\n%s", localStorage));
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("MyTag", "My message", e);
                }
            }
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
