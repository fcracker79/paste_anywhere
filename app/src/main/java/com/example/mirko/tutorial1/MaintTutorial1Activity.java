package com.example.mirko.tutorial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MaintTutorial1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.addMenuIem:
                Toast.makeText(MaintTutorial1Activity.this, "Adding!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteMenuItem:
                Toast.makeText(MaintTutorial1Activity.this, "Removing!", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalArgumentException("Unexpected " + id);
        }

        return true;
    }

    public void onClickGetDateTime(View v) {
        ((TextView) findViewById(R.id.dateTimeText)).setText(
                DateUtils.formatDateTime(this, new Date().getTime(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME)
        );
    }

    public void showSecondActivity(View v) {
        startActivity(new Intent(this, SecondActivity.class));
    }

}
