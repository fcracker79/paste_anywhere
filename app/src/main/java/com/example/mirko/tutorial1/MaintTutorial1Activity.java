package com.example.mirko.tutorial1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.Random;

public class MaintTutorial1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        final ArrayAdapter listAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,
                        new String[]{"a", "b", "c", "d"});

        final ListView listView = (ListView) findViewById(R.id.myAdapterListView);
        listView.setAdapter(listAdapter);
        listView.setSelection(R.id.selection);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MaintTutorial1Activity.this,
                        String.format("Item clicked: %s", parent.getItemAtPosition(position)),
                        Toast.LENGTH_SHORT).show();

            }
        });
        final Spinner spinner = findViewBy(R.id.myAdapterSpinner, Spinner.class);
        spinner.setAdapter(listAdapter);

        final GridView grid = findViewBy(R.id.myAdapterGridView, GridView.class);
        grid.setAdapter(listAdapter);
    }

    private <T extends View> T findViewBy(int d, Class<T> clazz) {
        return (T) findViewById(d);
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
