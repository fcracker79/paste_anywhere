package com.example.mirko.tutorial1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Files dir : %s\n", getFilesDir()));
        ((TextView) findViewById(R.id.localStorageText)).setText(sb);

        final List<String> data = new ArrayList<>();

        for (String sub : getFilesDir().list()) {
            data.add("Files");
            data.add(sub);
        }

        sb = new StringBuilder();
        sb.append(String.format("Cache dir : %s", getCacheDir()));
        ((TextView) findViewById(R.id.cacheStorageText)).setText(sb);

        for (String sub : getCacheDir().list()) {
            data.add("Cache");
            data.add(sub);
        }

        final GridView v = (GridView) findViewById(R.id.fileListView);
        v.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data));

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int elementPosition = position / 2;
                final String filename = data.get(elementPosition * 2 + 1);
                final File dir = "Files".equals(data.get(elementPosition * 2)) ?
                        getFilesDir() : getCacheDir();

                final String fullPathName = new File(dir.toString(), filename).toString();

                final StringBuilder content = new StringBuilder();

                InputStream is = null;

                try {
                    is = new FileInputStream(fullPathName);
                    final BufferedReader r = new BufferedReader(
                            new InputStreamReader(is)
                    );
                    for (String s = r.readLine(); s != null; s = r.readLine()) {
                        content.append(s).append("\n");
                    }
                    Toast.makeText(MaintTutorial1Activity.this, content, Toast.LENGTH_LONG).show();
                } catch(IOException e) {
                    Toast.makeText(
                            MaintTutorial1Activity.this,
                            String.format("Error opening %s: %s", fullPathName, e.getMessage()),
                            Toast.LENGTH_LONG).show();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            Log.e("Boh", e.getMessage(), e);
                        }
                    }
                }
            }
        });
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
