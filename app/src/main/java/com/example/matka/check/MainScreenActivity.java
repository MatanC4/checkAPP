package com.example.matka.check;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainScreenActivity extends FragmentActivity implements CategoriesListView.OnFragmentInteractionListener, UpNextListView.OnFragmentInteractionListener {

    private CategoriesListView categoriesListView;
    private UpNextListView upNextListView;
    CollectionPagerAdapter collectionPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        categoriesListView = CategoriesListView.newInstance();
        upNextListView = UpNextListView.newInstance();
        bindUi();
    }


    private void bindUi() {
        collectionPagerAdapter =  new CollectionPagerAdapter(getSupportFragmentManager(), categoriesListView ,upNextListView);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(collectionPagerAdapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
