package com.example.matka.check;

/**
 * Created by matka on 11/03/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by matka on 13/01/17.
 */

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
    private CategoriesListView categoriesListView;
    private UpNextListView upNextListView;

    public CollectionPagerAdapter(FragmentManager fm , CategoriesListView categoriesListView , UpNextListView upNextListView) {
        super(fm);
        this.categoriesListView = categoriesListView;
        this.upNextListView = upNextListView;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return this.categoriesListView;

             case 1:
                 return this.upNextListView;

            default:
                return null;
        }
    }

    @Override
    public int getCount () {
        return 2;
    }

    @Override
    public CharSequence getPageTitle ( int position){
        if (position == 0)
            return ("CATEGORIES");
        else
            return ("UP NEXT");
    }
}


