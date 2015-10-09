package com.example.mirko.tutorial1;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private volatile int createCounter = 0;
    private volatile int destroyCounter = 0;
    private volatile int unoStatoACaso = 0;
    public static final String EXTRA_MESSAGE = "extraMessage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);

        unoStatoACaso = 12345;

        setContentView(R.layout.activity_second);

        ((TextView) findViewById(R.id.secondActivityText)).setText(
                getIntent().getStringExtra(EXTRA_MESSAGE)
        );

        Toast.makeText(this,
                String.format("Second activity onCreate: %d, uno stato a caso: %d",
                        createCounter++,
                        unoStatoACaso), Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,
                String.format("Second activity onDestroy: %d, uno stato a caso: %d",
                        destroyCounter++,
                        unoStatoACaso), Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }

    public void onClickDie(View v) {
        this.finish();
    }
}
