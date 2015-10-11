package com.example.mirko.tutorial1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mirko on 11/10/15.
 */
public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return ViewPagerFragment1.newInstance("Ciccio", "baciccio");
            case 1:
                return ViewPagerFragment2.newInstance("Ciccio", "baciccio");
            case 2:
                return ViewPagerFragment3.newInstance("Ciccio", "baciccio");
            default:
                throw new IllegalArgumentException("Unexpected " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Pagina " + position;
    }
}
