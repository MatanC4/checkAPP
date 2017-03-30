package com.example.matka.check.Event;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;

import com.example.matka.check.R;
import com.squareup.picasso.Picasso;

import java.util.Timer;

import bl.controlers.AppManager;
import bl.entities.Event;
import bl.entities.EventStatus;


public class EventInfoFragment extends android.support.v4.app.Fragment {
    private ImageView imageView;
    private TextView title;
    private TextView description;
    private Button addBtn;
    private AppManager appManager;
    private Event event;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    private OnFragmentInteractionListener mListener;

    public EventInfoFragment() {
        // Required empty public constructor
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    // TODO: Rename and change types and number of parameters
    public static EventInfoFragment newInstance() {
        EventInfoFragment fragment = new EventInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        appManager = AppManager.getInstance(this.getContext());
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);
        imageView = (ImageView)view.findViewById(R.id.event_bg_image);
        title = (TextView) view.findViewById(R.id.event_title_event_info_screen);
        description = (TextView) view.findViewById(R.id.desc_textview);
        addBtn = (Button)view.findViewById(R.id.add_event_button);
        showButtonsAccordingToEventStatus(view ,event.getStatus());

        try {

            Picasso.with(this.getContext()).load(event.getImageURL()).into(imageView);
            //imageView  = appManager.getImageByKey(event.getImageURL()).
           // imageView = appManager.getImageByKey(event.getImageURL());
            title.setText(event.getName());
            description.setText(event.getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void showButtonsAccordingToEventStatus(View view , EventStatus eventStaus) {

        if(true){

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //appManager.addEvent( )
                    addBtn.setText("EVENT ADDED TO LIST");
                    Animation animFadeOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
                    animFadeOut.setDuration(2000);
                    addBtn.setAnimation(animFadeOut);
                    addBtn.setVisibility(View.GONE);
                }
            });
        }
        else if(eventStaus== EventStatus.VIEW){

        }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
