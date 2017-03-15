package com.example.matka.check;

/**
 * Created by matka on 11/03/17.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


//import android.app.Fragment;


public class CategoriesListView extends Fragment {

    private ArrayList<String> categoryList;
    private OnFragmentInteractionListener mListener;

    public CategoriesListView() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_list_view, container, false);
        //ScoreTable table;
        try{
            //table = SharedPreferencesHandler.getData(getContext());
        }
        catch(Exception e){
            //table = new ScoreTable();
        }




        categoryList = new ArrayList<String>();
        categoryList.add("MOVIES");
        categoryList.add("RESTAURANTS");
        categoryList.add("TRIPS");
        categoryList.add("SELF DEVELOPMENT");
        categoryList.add("BOOKS");
        categoryList.add("MUSIC");
        categoryList.add("GENERAL");



        // }
        ListView listView = (ListView) view.findViewById(R.id.category_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity()
                ,R.layout.custom_category_item_layout,R.id.category_list_item ,
                categoryList);


        listView.setAdapter(arrayAdapter);
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

    public static CategoriesListView newInstance() {
        CategoriesListView fragment = new CategoriesListView();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
