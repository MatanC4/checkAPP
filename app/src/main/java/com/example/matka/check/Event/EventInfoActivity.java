package com.example.matka.check.Event;

import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.example.matka.check.R;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.w3c.dom.Text;


public class EventInfoActivity extends AppCompatActivity implements EventInfoFragment.OnFragmentInteractionListener {

   private EventInfoFragment eventInfoActivity;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        setFragment(EventInfoFragment.newInstance());
    }

    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected void setFragment(EventInfoFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
        //imageView = (ImageView)findViewById((Integer) getIntent().getExtras("ImageViewID"));

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
