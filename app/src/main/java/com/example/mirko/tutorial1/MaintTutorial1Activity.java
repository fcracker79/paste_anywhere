package com.example.mirko.tutorial1;

import android.os.AsyncTask;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
                } catch (IOException e) {
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

        sb = new StringBuilder();
        sb.append(String.format("External storage: %s", getExternalFilesDir(null)));
        ((TextView) findViewById(R.id.externalStorageText)).setText(sb);
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

    public void onClickReadExternal(View v) {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                InputStream is = null;

                final File externalStorage = getExternalFilesDir(null);
                final File fileExternalStorage = new File(externalStorage.toString(), "pippo.txt");

                try {
                    is = new FileInputStream(fileExternalStorage);
                    final BufferedReader r = new BufferedReader(new InputStreamReader(is));

                    final StringBuilder sb = new StringBuilder();

                    for (String s = r.readLine(); s != null; s = r.readLine()) {
                        sb.append(s).append("\n");
                    }

                    Toast.makeText(MaintTutorial1Activity.this, sb, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            Log.e("Mah", e.getMessage(), e);
                        }
                    }
                }
            }
        };

        runOnUiThread(r);
    }

    public void onClickWriteExternal(View v) {
        final File externalStorage = getExternalFilesDir(null);
        final File fileExternalStorage = new File(externalStorage.toString(), "pippo.txt");

        final AsyncTask<String, Void, Boolean> myTask =
                new AsyncTask<String, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... params) {
                        for (String file : params) {

                            OutputStream os = null;
                            try {
                                os = new FileOutputStream(file, false);
                                final BufferedWriter w = new BufferedWriter(new OutputStreamWriter(os));
                                w.write("Lorem ipsum dolet\n");
                                w.write("Sic amet e poi boh non me lo ricordo più\n");
                                w.flush();
                            } catch (IOException e) {
                                return false;
                            } finally {
                                if (os != null) {
                                    try {
                                        os.close();
                                    } catch (IOException e) {
                                        Log.e("Boh?", e.getMessage(), e);
                                    }
                                }
                            }
                        }

                        return true;
                    }
                };
        myTask.execute(fileExternalStorage.toString());

    }
}
