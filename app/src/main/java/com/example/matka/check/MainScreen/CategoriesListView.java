package com.example.matka.check.MainScreen;

/**
 * Created by matka on 11/03/17.
 */

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
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.matka.check.Category.CategoryActivity;
import com.example.matka.check.R;

import java.util.ArrayList;

import bl.entities.Category;
import bl.entities.CategoryName;


//import android.app.Fragment;


public class CategoriesListView extends Fragment {

    private ArrayList<String> categoryList;
    private OnFragmentInteractionListener mListener;
    private Intent intent;

    public CategoriesListView() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
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
        for(CategoryName name:CategoryName.values()){
            categoryList.add(name.toString());
        }




        // }
        ListView  listView = (ListView) view.findViewById(R.id.category_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity()
                ,R.layout.custom_category_item_layout,R.id.category_list_item ,
                categoryList);


        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent  = new Intent(getContext() , CategoryActivity.class);
                switch (i) {
                    case 0:
                        startActivity(intent);

                    case 1:
                        startActivity(intent);

                    default:
                        startActivity(intent);
                }
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

    public static CategoriesListView newInstance() {
        CategoriesListView fragment = new CategoriesListView();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
