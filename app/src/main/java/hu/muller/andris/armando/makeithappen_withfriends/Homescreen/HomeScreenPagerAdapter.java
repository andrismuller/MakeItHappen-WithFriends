package hu.muller.andris.armando.makeithappen_withfriends.Homescreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hu.muller.andris.armando.makeithappen_withfriends.Homescreen.applications.ApplicationsFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Homescreen.duties.DutiesFragment;

/**
 * Created by Muller Andras on 9/27/2017.
 */

class HomeScreenPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 2;

    public HomeScreenPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DutiesFragment();
            case 1: return new ApplicationsFragment();
            default: return new DutiesFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
