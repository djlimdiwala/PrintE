package com.example.lkh.printe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DJL on 4/1/2018.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {


    private String title[] = {"InProgress", "Completed"};
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

//    @Override
//    public Fragment getItem(int position) {
//        return TabFragment.getInstance(position);
//    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                fragment_inprogress tab1 = new fragment_inprogress();
                return tab1;
            case 1:
                fragment_completed tab2 = new fragment_completed();
                return tab2;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
