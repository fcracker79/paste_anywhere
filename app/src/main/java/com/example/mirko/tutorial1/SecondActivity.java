package com.example.mirko.tutorial1;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "extraMessage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ((TextView) findViewById(R.id.secondActivityText)).setText(
                getIntent().getStringExtra(EXTRA_MESSAGE)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        Toast.makeText(this, "Second activity onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Second activity onDestroy", Toast.LENGTH_SHORT).show();;
        super.onDestroy();
    }
}
