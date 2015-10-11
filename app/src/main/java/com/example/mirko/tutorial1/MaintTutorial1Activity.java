package com.example.mirko.tutorial1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MaintTutorial1Activity extends AppCompatActivity implements ViewPagerFragment1.OnFragmentInteractionListener, ViewPagerFragment2.OnFragmentInteractionListener, ViewPagerFragment3.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maint_tutorial1, menu);

        final ViewPager vp = (ViewPager) findViewById(R.id.myPager);

        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(MaintTutorial1Activity.this, "Cucu + " + uri, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }


}
