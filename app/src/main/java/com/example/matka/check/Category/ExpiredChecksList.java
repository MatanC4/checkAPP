package com.example.matka.check.Category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matka.check.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpiredChecksList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpiredChecksList extends Fragment {

    private ArrayList<String> expiredItems;
    private OnFragmentInteractionListener mListener;
    private Intent intent;

    public ExpiredChecksList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expired_checks, container, false);
        //ScoreTable table;
        try{
            //table = SharedPreferencesHandler.getData(getContext());
        }
        catch(Exception e){
            //table = new ScoreTable();
        }

        expiredItems = new ArrayList<String>();
        expiredItems.add("Mcdonalds");
        expiredItems.add("Said 2000");
        expiredItems.add("Goocha");
        expiredItems.add("Girraf");



        // }
        ListView listView = (ListView) view.findViewById(R.id.expired_checks_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity()
                ,R.layout.custom_category_item_layout,R.id.category_list_item ,
                expiredItems);


        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }


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

    public static ExpiredChecksList newInstance() {
        ExpiredChecksList fragment = new ExpiredChecksList();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
