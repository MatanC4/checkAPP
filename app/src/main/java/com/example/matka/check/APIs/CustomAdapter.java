package com.example.matka.check.APIs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matka.check.Event.EventActivity;
import com.example.matka.check.Event.EventInfoActivity;
import com.example.matka.check.Event.EventInfoFragment;
import com.example.matka.check.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import bl.controlers.AppManager;
import bl.entities.Event;
import bl.entities.EventStatus;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RowItem> rowItems;
    private ArrayList<Event> arrayList;
    private Intent intent;
    private ViewHolder holder;
    private AppManager appManager;

    public CustomAdapter(Context context, List<RowItem> rowItems, ArrayList<Event> arrayList) {
        this.context = context;
        this.rowItems = rowItems;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView eventImage;
        TextView title;
        ImageButton addBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        holder = null;
        appManager = AppManager.getInstance(CustomAdapter.this.context);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.api_results_item_layout, null);
        holder = new ViewHolder();

        holder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
        if(arrayList.get(position).getStatus()== EventStatus.VIEW) {
            Picasso.with(context).load(arrayList.get(position).getImageURL()).into(holder.eventImage);
        }
        else{
            Log.d("STATUS", arrayList.get(position).getStatus().toString());
            Bitmap image;
            try {
                image = appManager.getImageFromStorage(arrayList.get(position));
                holder.eventImage.setImageBitmap(image);
            }
            catch(Exception e){
                Log.v("path from CustomAdapter",arrayList.get(position).getImageURL());
                e.printStackTrace();
                holder.eventImage.setImageResource(rowItems.get(position).getEventImageId());
            }
        }
        holder.title = (TextView) convertView
                .findViewById(R.id.event_name);
        holder.addBtn = (ImageButton) convertView
                .findViewById(R.id.add_btn);
        final RowItem row_pos = rowItems.get(position);
        holder.title.setText(row_pos.getTitle());
        holder.addBtn.setImageResource(row_pos.getAddButtonImage());
        convertView.setTag(holder);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextActivity(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextActivity(position);
            }
        });
        return convertView;
    }

    private void getNextActivity(int position){
        intent = new Intent(CustomAdapter.this.context , EventInfoActivity.class);
        Gson gson = new Gson();
        String event = gson.toJson(arrayList.get(position));
        intent.putExtra("EventObj",event);
        CustomAdapter.this.context.startActivity(intent);
    }
}
