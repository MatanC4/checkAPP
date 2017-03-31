package com.example.matka.check.Category;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by matka on 21/03/17.
 */
public class CategoryScreenCollectionPagerAdapter extends FragmentStatePagerAdapter {
/*    private CheckedList checkedList;
    private ExpiredChecksList expiredChecksList;
    private ToCheckList toCheckList;*/

    private EventsList checkedList;
    private EventsList expiredChecksList;
    private EventsList toCheckList;

/*    public CategoryScreenCollectionPagerAdapter(FragmentManager fm , CheckedList checkedList
            , ExpiredChecksList expiredChecksList , ToCheckList toCheckList) {
        super(fm);
        this.checkedList = checkedList;
        this.expiredChecksList = expiredChecksList;
        this.toCheckList = toCheckList;
    }*/

    public CategoryScreenCollectionPagerAdapter(FragmentManager fm , EventsList checkedList
            , EventsList expiredChecksList , EventsList toCheckList) {
        super(fm);
        this.checkedList = checkedList;
        this.expiredChecksList = expiredChecksList;
        this.toCheckList = toCheckList;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 1:
                return this.checkedList;
            case 2:
                return this.expiredChecksList;
            case 0:
            default:
                return this.toCheckList;
        }
    }

    @Override
    public int getCount () {
        return 3;
    }

    @Override
    public CharSequence getPageTitle ( int position){
        if (position == 0)
            return ("TO CHECK");
        else if (position == 1)
            return ("CHECKED");
        else
            return ("EXPIRED");
    }
}



