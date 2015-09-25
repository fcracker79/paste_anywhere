package com.example.mirko.tutorial1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MaintTutorial1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        final WebView myBrowser = (WebView) findViewById(R.id.myBrowser);
        myBrowser.getSettings().setJavaScriptEnabled(true);

        myBrowser.setWebViewClient(new WebViewClient() {
            @Override
            // This is to prevent redirects from opening another page rather than reusing the
            // current display area
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(MaintTutorial1Activity.this,
                        String.format("Opening %s at %s",
                                url,
                                DateUtils.formatDateTime(
                                        MaintTutorial1Activity.this,
                                        new Date().getTime(),
                                        DateUtils.FORMAT_SHOW_DATE
                                                | DateUtils.FORMAT_SHOW_TIME
                                        )),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        myBrowser.loadUrl("http://www.google.it");

        final EditText e = (EditText) findViewById(R.id.urlField);

        e.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        final String data = s.toString();
                        if (data.startsWith("<html>")) {
                            myBrowser.loadData(data, "text/html", "utf-8");
                        } else {
                            myBrowser.loadUrl(data);
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maint_tutorial1, menu);

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
