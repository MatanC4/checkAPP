package com.example.matka.check.MainScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.example.matka.check.R;

import java.util.Calendar;

import bl.notifications.EventNotification;
import bl.notifications.NotificationService;

public class MainScreenActivity extends FragmentActivity implements CategoriesListView.OnFragmentInteractionListener, UpNextListView.OnFragmentInteractionListener {

    private CategoriesListView categoriesListView;
    private UpNextListView upNextListView;
    private MainScreenCollectionPagerAdapter mainScreenCollectionPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        categoriesListView = CategoriesListView.newInstance();
        upNextListView = UpNextListView.newInstance();
        bindUi();
       // setDummyNotificaion();
        setDummyNotificationWithBroadcast();
    }

    private void setDummyNotificaion(){
        Calendar sevendayalarm = Calendar.getInstance();
        sevendayalarm.add(Calendar.MINUTE, 1);
        Intent intent = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 001, intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, sevendayalarm.getTimeInMillis(), pendingIntent);
        Log.d("ALARM","set to: "+sevendayalarm.getTime());
    }

    private void setDummyNotificationWithBroadcast(){
        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        EventNotification receiver = new EventNotification();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("param", "My scheduled action");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        // I choose 3s after the launch of my application
        alarms.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000, operation) ;
        intent.putExtra("param", "My 2nd scheduled action");
        alarms.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+15000, operation);
    }


    private void bindUi() {
        mainScreenCollectionPagerAdapter =  new MainScreenCollectionPagerAdapter(getSupportFragmentManager(), categoriesListView ,upNextListView);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mainScreenCollectionPagerAdapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
