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

import com.example.matka.check.APIs.APIresActivity;
import com.example.matka.check.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckedList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CheckedList extends Fragment {

    private ArrayList<String> checkedItems;
    private OnFragmentInteractionListener mListener;
    private Intent intent;
    public CheckedList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checked, container, false);
        //ScoreTable table;
        try{
            //table = SharedPreferencesHandler.getData(getContext());
        }
        catch(Exception e){
            //table = new ScoreTable();
        }

        checkedItems = new ArrayList<String>();
        checkedItems.add("Taizu");
        checkedItems.add("Topolopompo");
        checkedItems.add("Thai House");
        checkedItems.add("Messa");



        // }
        ListView listView = (ListView) view.findViewById(R.id.checked_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity()
                ,R.layout.custom_category_item_layout,R.id.category_list_item ,
                checkedItems);


        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent  = new Intent(getContext() , APIresActivity.class);
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

    public static CheckedList newInstance() {
        CheckedList fragment = new CheckedList();
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
