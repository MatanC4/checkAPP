package com.example.matka.check.Category;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matka.check.R;

import bl.entities.EventStatus;

import bl.entities.CategoryName;

public class CategoryActivity extends AppCompatActivity implements ExpiredChecksList.OnFragmentInteractionListener , ToCheckList.OnFragmentInteractionListener ,CheckedList.OnFragmentInteractionListener {

/*    private CheckedList checkedList;
    private ExpiredChecksList expiredChecksList;
    private ToCheckList toCheckList*/;
    private EventsList checkedList;
    private EventsList expiredChecksList;
    private EventsList toCheckList;
    private CategoryScreenCollectionPagerAdapter categoryScreenCollectionPagerAdapter;
    private ViewPager mViewPager;
    private CategoryName categoryName;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryName = (CategoryName) getIntent().getSerializableExtra("Category");

        toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        //Log.v("Cat name" , categoryName.toString());
        toolbarTitle.setText(categoryName.toString());
/*      checkedList = CheckedList.newInstance();
        expiredChecksList = ExpiredChecksList.newInstance();
        toCheckList = ToCheckList.newInstance();*/

        checkedList = EventsList.newInstance();
        expiredChecksList = EventsList.newInstance();
        toCheckList = EventsList.newInstance();

        checkedList.setCategoryName(categoryName);
        expiredChecksList.setCategoryName(categoryName);
        toCheckList.setCategoryName(categoryName);

        toCheckList.setStatus(EventStatus.TODO);
        expiredChecksList.setStatus(EventStatus.EXPIRED);
        checkedList.setStatus(EventStatus.DONE);

        bindUi();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/

    }


    private void bindUi() {
        categoryScreenCollectionPagerAdapter =  new CategoryScreenCollectionPagerAdapter(getSupportFragmentManager(),checkedList,expiredChecksList,toCheckList);
        mViewPager = (ViewPager) findViewById(R.id.container);
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
      /*  public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }**/

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
}
