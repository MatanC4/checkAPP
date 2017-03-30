package com.example.matka.check.Event;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.example.matka.check.R;
import com.google.gson.Gson;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.w3c.dom.Text;

import bl.controlers.AppManager;
import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;


public class EventInfoActivity extends AppCompatActivity implements EventInfoFragment.OnFragmentInteractionListener {

   private EventInfoFragment eventInfoActivity;
    private ImageView imageView;
    private AppManager appManager;
    private Event event;
    private EventInfoFragment eventInfoFragment;
    private CategoryName categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Gson gson = new Gson();
        event = gson.fromJson(getIntent().getExtras().getString("EventObj"),Event.class);
        categoryName = (CategoryName) getIntent().getSerializableExtra("Category");

        eventInfoFragment = EventInfoFragment.newInstance();
        eventInfoFragment.setEvent(event);
        setFragment(eventInfoFragment);
    }

    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected void setFragment(EventInfoFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
        //Im = (Bitmap) findViewById(getIntent().getExtras("ImageViewID"));

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
