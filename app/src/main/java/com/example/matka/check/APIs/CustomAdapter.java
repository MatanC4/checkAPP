package com.example.matka.check.APIs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
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

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RowItem> rowItems;
    private ArrayList<Event> arrayList;
    private Intent intent;
    private int imageIdToPassToIntent;
    private ViewHolder holder;
    private ByteArrayOutputStream byteArrayOutputStream;
    private AppManager appManager;
    private int positionOfEvent;

    CustomAdapter(Context context, List<RowItem> rowItems, ArrayList<Event> arrayList) {
        this.context = context;
        this.rowItems = rowItems;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.api_results_item_layout, null);
            holder = new ViewHolder();

            holder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
            Picasso.with(context).load(arrayList.get(position).getImageURL()).into(holder.eventImage);
            appManager.temporarilyStoreImage(arrayList.get(position).getImageURL(),holder.eventImage);

            //prepare image to be passed in intent
           /*Bitmap bitmap = (((BitmapDrawable)holder.eventImage.getDrawable())).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG , 50 ,byteArrayOutputStream);**/


            holder.title = (TextView) convertView
                    .findViewById(R.id.event_name);
            holder.addBtn = (ImageButton) convertView
                    .findViewById(R.id.add_btn);
            final RowItem row_pos = rowItems.get(position);
            //holder.eventImage.setImageResource(row_pos.getEventImageId());
            holder.title.setText(row_pos.getTitle());
            holder.addBtn.setImageResource(row_pos.getAddButtonImage());
            convertView.setTag(holder);

            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(CustomAdapter.this.context , EventInfoActivity.class);
                    Gson gson = new Gson();
                    String event = gson.toJson(arrayList.get(position));
                    intent.putExtra("EventObj",event);
                    CustomAdapter.this.context.startActivity(intent);
                }
            });

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }




}
