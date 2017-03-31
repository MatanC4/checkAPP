package com.example.matka.check.Event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.app.DialogFragment;
import android.widget.Toast;

import com.example.matka.check.Date.DatePickerFragment;
import com.example.matka.check.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Timer;

import bl.controlers.AppManager;
import bl.entities.Amendment;
import bl.entities.AmendmentType;
import bl.entities.Event;
import bl.entities.EventStatus;


public class EventInfoFragment extends android.support.v4.app.Fragment implements DatePickerDialog.OnDateSetListener {
    private ImageView imageView;
    private TextView title;
    private TextView description;
    private Button addBtn;
    private AppManager appManager;
    private Event event;
    private FloatingActionButton fab;
    private ImageView imageForFab;
    private Button checkBtn;
    private Button removeBtn;
    private LinearLayout ll;
    private int year , month , day;
    final static int DATE_DIALOG_ID = 0;
    private FragmentManager fragmentManager;
    private Button commitBtn;

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
        //fab = (FloatingActionButton)view.findViewById(R.id.delete_red_x_button);
        //fab.setVisibility(View.VISIBLE);



        imageForFab = new ImageView(this.getContext());
        imageForFab.setImageResource(R.drawable.plus_blue_128px);


       // FloatingActionButton actionButton = new FloatingActionButton.Builder.
         //       .setContentView(imageForFab)
           //     .build();




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

    private void showButtonsAccordingToEventStatus(View view , final EventStatus eventStaus) {
        addBtn = (Button)view.findViewById(R.id.add_event_button);
        removeBtn = (Button)view.findViewById(R.id.remove_event_btn);
        ll = (LinearLayout)view.findViewById(R.id.linear_buttons_holder);
        checkBtn = (Button)view.findViewById(R.id.check_event_btn);
        ll.setVisibility(View.GONE);

        if(true) {
            if (eventStaus == EventStatus.VIEW) {
                addBtn.setVisibility(View.VISIBLE);
                //ll.setVisibility(View.GONE);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater(null).inflate(R.layout.dialog_add_event_details ,null);
                    myBuilder.setView(mView);
                    AlertDialog dialog = myBuilder.create();
                    dialog.show();
                    final EditText timeToComplete = (EditText)mView.findViewById(R.id.time_limit_edit_text);
                    final EditText incentiveDescription = (EditText)mView.findViewById(R.id.describe_inentive_textbox);
                    commitBtn = (Button) mView.findViewById(R.id.commit_button_123);
                    final RadioGroup rg = (RadioGroup)mView.findViewById(R.id.incentiveRadioGroup);

                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!timeToComplete.getText().toString().matches("") && Integer.parseInt(timeToComplete.getText().toString()) < 365  &&
                                    !(rg.getCheckedRadioButtonId() == -1) &&
                                    !incentiveDescription.getText().toString().matches("")){
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.DATE,Integer.parseInt(timeToComplete.getText().toString()) );
                                event.setDueDate(cal);
                                Amendment am = new Amendment();
                                am.setDescription(incentiveDescription.toString());
                                am.setType(getAmendmantFromUI(rg));
                                event.setAmendment(am);
                                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                try{
                                   appManager.addEvent(getContext() , event , bitmap);
                                    Toast.makeText(getActivity(), "new event added ",
                                            Toast.LENGTH_LONG).show();

                                }catch (Exception e){
                                    Toast.makeText(getActivity(), "failed saving event ",
                                            Toast.LENGTH_LONG).show();
                                    Log.v("Crash on saving " , e.getMessage());
                                    //Log.v("Crash on saving " , e.getStackTrace().toString());
                                    e.printStackTrace();
                                }




                            }else{
                                Toast.makeText(getActivity(), "seems some info is missing.. ",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            });
        }
        else if(eventStaus == EventStatus.VIEW){

        }
    }
    }

    private AmendmentType getAmendmantFromUI(RadioGroup rg) {
        int  i  = rg.getCheckedRadioButtonId();
        switch (i){

            case 1:
                return AmendmentType.VOLUNTEERING;
            case 2:
                return AmendmentType.WORKOUT;
            case 3:
                return AmendmentType.STUDY;
            case 0:
            default:
                return AmendmentType.DONATION;
        }

    }


    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getActivity().getFragmentManager(), "datePicker");
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





    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        //event.setDueDate();
        //appManager.addEvent( )
        addBtn.setText("EVENT ADDED TO LIST");
        Animation animFadeOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        animFadeOut.setDuration(2000);
        addBtn.setAnimation(animFadeOut);
        addBtn.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
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
