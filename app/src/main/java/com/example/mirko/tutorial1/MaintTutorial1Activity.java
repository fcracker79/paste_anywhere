package com.example.mirko.tutorial1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MaintTutorial1Activity extends AppCompatActivity implements MyButton.OnFragmentInteractionListener, ListOfElementsFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        final LinearLayout container = (LinearLayout) findViewById(R.id.containerForListFragment);
        if (container.getChildCount() == 0) {
            getFragmentManager().beginTransaction()
                    .add(R.id.containerForListFragment, new ListOfElementsFragment())
                    .commit();
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

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, String.format("onFragmentInteration(uri) %s", uri.toString()), Toast.LENGTH_SHORT);
    }

    @Override
    public void onFragmentInteraction(String id) {
        Toast.makeText(this, String.format("onFragmentInteration(id) %s", id), Toast.LENGTH_SHORT);
    }
}
