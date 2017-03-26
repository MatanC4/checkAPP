package com.example.matka.check.APIs;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import bl.entities.Event;
import com.example.matka.check.R;

import java.util.ArrayList;

/**
 * Created by matka on 25/03/17.
 */
public class CustomArrayAdapter extends ArrayAdapter<Event> {

    private final Activity context;
    private final String [] titles;
    //private final int [] imageId;


    public CustomArrayAdapter(Activity context,
                              String [] titles) {
        super(context, R.layout.activity_apires);
        this.context = context;
        this.titles = titles;
        //this.imageId = imageId;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.api_results_item_layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.event_name);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.event_image);
        txtTitle.setText(titles[position]);

       // imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
