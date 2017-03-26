package com.example.matka.check.APIs;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.matka.check.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bl.entities.Event;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RowItem> rowItems;
    private ArrayList<Event> arrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.api_results_item_layout, null);
            holder = new ViewHolder();



            holder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
            Picasso.with(context).load(arrayList.get(position).getImageURL()).into(holder.eventImage);

            holder.title = (TextView) convertView
                    .findViewById(R.id.event_name);

            holder.addBtn = (ImageButton) convertView
                    .findViewById(R.id.add_btn);

            RowItem row_pos = rowItems.get(position);

            //holder.eventImage.setImageResource(row_pos.getEventImageId());
            holder.title.setText(row_pos.getTitle());

            holder.addBtn.setImageResource(row_pos.getAddButtonImage());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
