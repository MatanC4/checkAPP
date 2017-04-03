package com.example.matka.check.MainScreen;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.matka.check.R;

public class MainScreenActivity extends FragmentActivity implements CategoriesListView.OnFragmentInteractionListener, SpecialEventsList.OnFragmentInteractionListener, ViewPager.OnPageChangeListener{

    private CategoriesListView categoriesListView;
    private SpecialEventsList upNext;
    private SpecialEventsList suggestions;
    private MainScreenCollectionPagerAdapter mainScreenCollectionPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        categoriesListView = CategoriesListView.newInstance();
        upNext = SpecialEventsList.newInstance();
        suggestions = SpecialEventsList.newInstance();
        suggestions.setSuggestions(true);
        bindUi();
    }

    private void bindUi() {
        mainScreenCollectionPagerAdapter = new MainScreenCollectionPagerAdapter(getSupportFragmentManager(), categoriesListView, upNext, suggestions);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mainScreenCollectionPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        //slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_tab_strip);
        //slidingPaneLayout.setDistributeEvenly(true);
        //slidingPaneLayout.setViewPager(mViewPager);
        rl = (RelativeLayout) findViewById(R.id.relative_main_layout);
        rl.invalidate();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==2)
            if(suggestions.isAnonymous()){
                Snackbar s = Snackbar.make(suggestions.getView(),suggestions.getMessage(),Snackbar.LENGTH_SHORT);
                s.show();
            }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
