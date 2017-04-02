package com.example.matka.check.MainScreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matka.check.APIs.CustomAdapter;
import com.example.matka.check.APIs.RowItem;
import com.example.matka.check.R;

import java.util.ArrayList;

import bl.controlers.AppManager;
import bl.entities.CategoryName;
import bl.entities.Event;


public class SpecialEventsList extends Fragment {

    private ArrayList<Event> eventsForDisplay;
    private OnFragmentInteractionListener mListener;
    private boolean isSuggestions = false;
    private boolean isAnonymous = false;
    private String message;

    public boolean isSuggestions() {
        return isSuggestions;
    }

    public void setSuggestions(boolean suggestions) {
        isSuggestions = suggestions;
    }

    public SpecialEventsList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_up_next_list_view, container, false);
        eventsForDisplay = new ArrayList<>();
        AppManager manager = AppManager.getInstance(getContext());
        if(!isSuggestions)
            eventsForDisplay = manager.getNext5Events();
        else{
            try{
/*                eventsForDisplay = manager.getSuggestionsByProfile(CategoryName.values()
                        [(int)(Math.random()*CategoryName.values().length)],getContext());*/
                eventsForDisplay = manager.getSuggestionsByProfile(CategoryName.MOVIES,getContext());
            }
            catch(Exception e){
                isAnonymous = true;
                message = e.getMessage();
                eventsForDisplay = new ArrayList<>();
            }
        }

        ArrayList<RowItem> rowItems = new ArrayList<>();
        try{
            for (Event event :  eventsForDisplay) {
                RowItem item = new RowItem(event.getName(),
                        R.drawable.millennial_explorers,
                        R.drawable.ic_add_circle_outline);
                rowItems.add(item);
            }
        }
        catch(Exception e){}

        ListView lView = (ListView) view.findViewById(R.id.up_next_list_view_ListView);
        CustomAdapter adapter = new CustomAdapter(this.getContext(), rowItems, eventsForDisplay);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public static SpecialEventsList newInstance()  {
        SpecialEventsList fragment = new SpecialEventsList();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String getMessage() {
        return message;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }
}
