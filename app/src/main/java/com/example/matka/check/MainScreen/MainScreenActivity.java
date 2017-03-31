package com.example.matka.check.MainScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.util.Log;

import com.example.matka.check.R;

import java.util.Calendar;

import bl.controlers.AppManager;
import bl.entities.Event;
import bl.notifications.EventNotification;
import bl.notifications.NotificationService;

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

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
