package com.example.matka.check.MainScreen;

/**
 * Created by matka on 11/03/17.
 */

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;


/**
 * Created by matka on 13/01/17.
 */

public class MainScreenCollectionPagerAdapter extends FragmentStatePagerAdapter {
    private CategoriesListView categoriesListView;
    private SpecialEventsList upNext;
    private SpecialEventsList suggestions;

    public MainScreenCollectionPagerAdapter(FragmentManager fm, CategoriesListView categoriesListView, SpecialEventsList upNext, SpecialEventsList suggestions) {
        super(fm);
        this.categoriesListView = categoriesListView;
        this.upNext = upNext;
        this.suggestions = suggestions;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return this.categoriesListView;

            case 1:
                return this.upNext;

            case 2:
                return this.suggestions;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return ("CATEGORIES");
        else if (position == 1)
            return ("UP NEXT");
        else
            return ("SUGGESTIONS");
    }
}


