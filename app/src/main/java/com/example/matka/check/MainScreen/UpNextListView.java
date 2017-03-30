package com.example.matka.check.MainScreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matka.check.APIs.CustomAdapter;
import com.example.matka.check.APIs.RowItem;
import com.example.matka.check.R;

import java.util.ArrayList;

import bl.controlers.AppManager;
import bl.entities.Event;


public class UpNextListView extends Fragment {


    private ArrayList upNextList;
    private OnFragmentInteractionListener mListener;

    public UpNextListView() {
    }

    public void sadsd(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_up_next_list_view, container, false);

/*        upNextList = new ArrayList<>();
        upNextList.add("first");
        upNextList.add("second");
        upNextList.add("third");

        ListView listView = (ListView) view.findViewById(R.id.up_next_list_view_ListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                upNextList);
        listView.setAdapter(arrayAdapter);*/
        AppManager manager = AppManager.getInstance(getContext());
        ArrayList<Event> upNext = manager.getNext5Events();
        ArrayList<RowItem> rowItems = new ArrayList<>();
        for (Event event :  upNext) {
            RowItem item = new RowItem(event.getName(),
                    R.drawable.millennial_explorers,
                    R.drawable.plus_1);
            rowItems.add(item);
        }
        ListView myListview = (ListView) view.findViewById(R.id.up_next_list_view_ListView);
        CustomAdapter adapter = new CustomAdapter(this.getContext(), rowItems, upNext);
        myListview.setAdapter(adapter);

        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public static UpNextListView newInstance()  {
        UpNextListView fragment = new UpNextListView();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
