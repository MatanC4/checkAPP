package com.example.matka.check.MainScreen;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.matka.check.R;

public class MainScreenActivity extends FragmentActivity implements CategoriesListView.OnFragmentInteractionListener, UpNextListView.OnFragmentInteractionListener {

    private CategoriesListView categoriesListView;
    private UpNextListView upNextListView;
    private MainScreenCollectionPagerAdapter mainScreenCollectionPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        categoriesListView = CategoriesListView.newInstance();
        upNextListView = UpNextListView.newInstance();
        bindUi();
    }


    private void bindUi() {
        mainScreenCollectionPagerAdapter =  new MainScreenCollectionPagerAdapter(getSupportFragmentManager(), categoriesListView ,upNextListView);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mainScreenCollectionPagerAdapter);
        rl = (RelativeLayout)findViewById(R.id.relative_main_layout);
        rl.invalidate();
        //fab = (FloatingActionButton) findViewById(R.id.add_eve_via_api__button);
        //fab.bringToFront();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
