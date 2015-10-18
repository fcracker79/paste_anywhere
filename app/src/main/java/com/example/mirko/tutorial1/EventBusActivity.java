package com.example.mirko.tutorial1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EventBusActivity extends AppCompatActivity {
    private static final String[] VALUES = {
            "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed",
            "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "dolore", "magna", "aliqua",
            "enim", "minim", "veniam", "quis", "nostrud", "exercitation", "ullamco", "laboris",
            "nisi", "aliquip", "commodo", "consequat", "Duis", "aute", "irure", "dolor",
            "reprehenderit", "voluptate", "velit", "esse", "cillum", "dolore", "fugiat", "nulla",
            "pariatur", "excepteur", "sint", "occaecat", "cupidatat", "proident", "sunt", "culpa",
            "officia", "deserunt", "mollit", "anim", "laborum"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content,
                            EventBusFragment.newInstance(VALUES)).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_bus, menu);
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
