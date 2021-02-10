package com.example.eventmanager.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eventmanager.Fragments.Fragment_current;
import com.example.eventmanager.Fragments.Fragment_past;
import com.example.eventmanager.Fragments.Fragments_upcoming;
import com.example.eventmanager.R;
import com.example.eventmanager.ui.main.PlaceholderFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tabPast, R.string. tabCurrent,R.string.tabFuture};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment= null;
       switch (position){
           case 0:
               fragment = new Fragment_past();
               break;
           case  1:
               fragment  = new Fragment_current();
               break;
           case 2:
               fragment = new
                       Fragments_upcoming();
               break;
       }
       return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}