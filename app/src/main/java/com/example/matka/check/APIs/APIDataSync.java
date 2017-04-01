package com.example.matka.check.APIs;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bl.controlers.AppManager;
import bl.entities.AdditionToDescription;
import bl.entities.CategoryName;
import bl.entities.Event;
import bl.entities.EventStatus;

/**
 * Created by matka on 26/03/17.
 */
public class APIDataSync extends Thread implements Runnable {
    private Handler handler;
    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY = "8a5c1fef1a13c3293e4c069fde43be81";
    private String inputLang = "en-US";
    private final String getPopularMethod = "movie/popular?api_key=";
    private final String languagePrefix = "&language=en-US";
    private final String pagePrefix = "&page=1";
    private JsonObjectRequest jsonObjectRequest;
    private final int NUM_OF_RES = 20;
    private final  String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";
    private final String [] IMAGE_SIZE  =  {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
    private ArrayList<Event> popularMoviesList;
    private String popUrl;
    Context context;
    APIListener apiListener;
    AppManager appManager;


    public APIDataSync(String popUrl, Context context , APIListener apiListener){
        this.popUrl=popUrl;
        this.context=context;
        this.apiListener=apiListener;
        appManager = AppManager.getInstance(this.context);
    }

@Override
    public void run(){
        popularMoviesList = new ArrayList<>();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,popUrl ,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.v("MOVIE DB API" , "RESPONSE: " + response.toString());
                try {
                    JSONArray movieList = response.getJSONArray("results");
                    for (int i = 0; i<NUM_OF_RES ; i++){
                        Event event = new Event(i);
                        JSONObject movie = movieList.getJSONObject(i);
                        //Log.d("JSON MOVIE", movie.toString());
                        event.setId(movie.getInt("id"));
                        if (!appManager.isEventAlreadyExist(event.getId(),CategoryName.MOVIES)){
                            event.setDescription(movie.getString("overview"));
                            event.setImageURL(BASE_URL_IMAGE + IMAGE_SIZE[4]+ movie.getString("backdrop_path"));
                            event.addToDescription(movie.getString("release_date"), AdditionToDescription.RELEASE_DATE);
                            event.setName(movie.getString("original_title"));
                            event.addToDescription(movie.getString("vote_average"), AdditionToDescription.SCORE);
                            event.setStatus(EventStatus.VIEW);
                            popularMoviesList.add(event);
                        }

                        //Log.v("MOVIE NAME " + i , event.toString());
                    }
                    //Log.v("all movies", popularMoviesList.toString());
                    apiListener.passArrayList(popularMoviesList);
                }catch (JSONException e){
                    Log.v("JSON Parsing" , "ERROR:" + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("MOVIE DB" , "ERROR: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);
        Log.v("i was here" , popularMoviesList.toString());
    }




}
