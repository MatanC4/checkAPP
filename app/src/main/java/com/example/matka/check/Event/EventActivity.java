package com.example.matka.check.Event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matka.check.R;


public class EventActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

    }
}
