package com.example.matka.check.Category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.matka.check.APIs.APIresActivity;
import com.example.matka.check.APIs.CustomAdapter;
import com.example.matka.check.APIs.RowItem;
import com.example.matka.check.R;

import java.util.ArrayList;

import bl.controlers.AppManager;
import bl.entities.CategoryName;
import bl.entities.Event;
import bl.entities.EventStatus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class EventsList extends android.support.v4.app.Fragment {
    private ArrayList<String> expiredItems;
    private EventsList.OnFragmentInteractionListener mListener;
    private Intent intent;
    private CategoryName categoryName;
    private ImageButton add;
    private EventStatus status;

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }


    public EventsList() {
        // Required empty public constructor
    }

    public static EventsList newInstance() {
        EventsList fragment = new EventsList();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        AppManager manager = AppManager.getInstance(getContext());
        ArrayList<Event> toCheck = manager.getEventsByStatus(categoryName, status);
        ArrayList<RowItem> rowItems = new ArrayList<>();
        for (Event event :  toCheck) {
            RowItem item = new RowItem(event.getName(),
                    R.drawable.millennial_explorers,
                    R.drawable.ic_add_circle_outline);
            rowItems.add(item);
        }
        ListView myListview = (ListView) view.findViewById(R.id.events_list_view);
        CustomAdapter adapter = new CustomAdapter(this.getContext(), rowItems, toCheck);
        myListview.setAdapter(adapter);

        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        add = (ImageButton) view.findViewById(R.id.add_eve_via_api__button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getContext() , APIresActivity.class);
                intent.putExtra("Category" ,categoryName);
                startActivity(intent);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventsList.OnFragmentInteractionListener) {
            mListener = (EventsList.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
