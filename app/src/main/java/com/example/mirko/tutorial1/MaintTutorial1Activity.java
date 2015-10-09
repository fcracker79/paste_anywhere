package com.example.mirko.tutorial1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MaintTutorial1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);
        Toast.makeText(this, "Main activity onCreate", Toast.LENGTH_SHORT).show();
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
        final Intent intent = new Intent(this, SecondActivity.class);
        final String message = getString(R.string.mostra_sgorbio);
        intent.putExtra(SecondActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showImplicitActivity(View v) {
        final String uri = ((EditText) findViewById(R.id.implicitActivityUri)).getText().toString();

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Main activity onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Main activity onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Main activity onStop", Toast.LENGTH_SHORT).show();
    }
}
