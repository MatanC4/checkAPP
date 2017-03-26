package com.example.matka.check.Category;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.matka.check.MainScreen.CategoriesListView;
import com.example.matka.check.MainScreen.MainScreenCollectionPagerAdapter;
import com.example.matka.check.MainScreen.UpNextListView;
import com.example.matka.check.R;

public class CategoryActivity extends AppCompatActivity implements ExpiredChecksList.OnFragmentInteractionListener , ToCheckList.OnFragmentInteractionListener ,CheckedList.OnFragmentInteractionListener {

    private CheckedList checkedList;
    private ExpiredChecksList expiredChecksList;
    private ToCheckList toCheckList;
    private CategoryScreenCollectionPagerAdapter categoryScreenCollectionPagerAdapter;
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        checkedList = CheckedList.newInstance();
        expiredChecksList = ExpiredChecksList.newInstance();
        toCheckList = ToCheckList.newInstance();
        bindUi();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    private void bindUi() {
        categoryScreenCollectionPagerAdapter =  new CategoryScreenCollectionPagerAdapter(getSupportFragmentManager(),checkedList,expiredChecksList,toCheckList);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(categoryScreenCollectionPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("Search");
        menu.add("Test");
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_category, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private CategoriesListView categoriesListView;
        private UpNextListView upNextListView;

        public SectionsPagerAdapter(FragmentManager fm) {
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
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}