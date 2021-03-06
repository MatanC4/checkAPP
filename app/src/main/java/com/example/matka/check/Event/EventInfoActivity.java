package com.example.matka.check.Event;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.matka.check.Category.CategoryActivity;
import com.example.matka.check.R;
import com.google.gson.Gson;
import android.util.Log;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import org.w3c.dom.Text;

import bl.controlers.AppManager;
import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;
import bl.notifications.BroadcastTags;


public class EventInfoActivity extends AppCompatActivity implements EventInfoFragment.OnFragmentInteractionListener {

   private EventInfoFragment eventInfoActivity;
    private ImageView imageView;
    private AppManager appManager;
    private Event event;
    private EventInfoFragment eventInfoFragment;
    private CategoryName categoryName;
    private boolean isFromService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Gson gson = new Gson();
        event = gson.fromJson(getIntent().getExtras().getString("EventObj"),Event.class);
        categoryName = (CategoryName) getIntent().getSerializableExtra("Category");
        isFromService = getIntent().getBooleanExtra(BroadcastTags.IS_FROM_BROADCAST ,false);
        eventInfoFragment = EventInfoFragment.newInstance();
        eventInfoFragment.setIsFromService(isFromService);
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

    @Override
    public void onBackPressed() {
        if(getIntent().getBooleanExtra("IS_CHECKED",false)) {
            Intent intent = new Intent(this, CategoryActivity.class);
            Log.v("After Event info", categoryName.toString());
            intent.putExtra("Category", categoryName);
            startActivity(intent);
        }
        else{
            super.onBackPressed();
        }
    }
}
