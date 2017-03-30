package com.example.matka.check.Category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.matka.check.APIs.APIresActivity;
import com.example.matka.check.R;

import java.util.ArrayList;

import bl.entities.CategoryName;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToCheckList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ToCheckList extends Fragment {

    private ArrayList<String> ToCheckItems;
    private OnFragmentInteractionListener mListener;
    private Intent intent;
    private CategoryName categoryName;
    private ImageButton add;


    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }


    public ToCheckList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_check, container, false);
        //ScoreTable table;
        try{
            //table = SharedPreferencesHandler.getData(getContext());
        }
        catch(Exception e){
            //table = new ScoreTable();
        }

        ToCheckItems = new ArrayList<String>();
        ToCheckItems.add("Alora");
        ToCheckItems.add("Barbary");
        ToCheckItems.add("Room Service");
        ToCheckItems.add("Zozobra");



        add = (ImageButton) view.findViewById(R.id.add_eve_via_api__button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity() , APIresActivity.class);
                intent.putExtra("Category" ,categoryName);
                startActivity(intent);
            }
        });



        ListView listView = (ListView) view.findViewById(R.id.to_check_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity()
                ,R.layout.custom_category_item_layout,R.id.category_list_item ,
                ToCheckItems);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent  = new Intent(getContext() , APIresActivity.class);
                intent.putExtra("Category" ,CategoryName.values()[i]);
                startActivity(intent);
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

    public static ToCheckList newInstance() {
        ToCheckList fragment = new ToCheckList();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
