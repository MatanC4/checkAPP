package com.example.matka.check.APIs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.matka.check.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bl.entities.AdditionToDescription;
import bl.entities.Event;
import bl.entities.EventStatus;


public class MoviesApi extends AppCompatActivity {

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY = "8a5c1fef1a13c3293e4c069fde43be81";
    private String inputLang = "en-US";
    private final String getPopularMethod = "movie/popular?api_key=";
    private final String languagePrefix = "&language=en-US";
    private final String pagePrefix = "&page=1";
    private JsonObjectRequest jsonObjectRequest;
    private final int NUM_OF_RES = 10;
    private final  String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";
    private final String [] IMAGE_SIZE  =  {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    //https://api.themoviedb.org/3/movie/popular?api_key=8a5c1fef1a13c3293e4c069fde43be81&language=en-US&page=1 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_api);

        String popUrl = BASE_URL + getPopularMethod + API_KEY + languagePrefix + pagePrefix;
        getPopularMovies(popUrl);

    }

    private void getPopularMovies(String popUrl) {

        ArrayList<Event> popularMoviesList = new ArrayList<Event>();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,popUrl ,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("MOVIE DB API" , "RESPONSE: " + response.toString());
                try {
                    //JSONObject responseText = response.getJSONObject("overview");

                    JSONArray movieList = response.getJSONArray("results");
                    for (int i = 0; i<NUM_OF_RES ; i++){
                        Event event = new Event(i);
                        JSONObject movie = movieList.getJSONObject(i);
                        event.setImageURL(BASE_URL_IMAGE + IMAGE_SIZE[2]+ movie.getString("poster_path"));
                        event.setDescription(movie.getString("overview"));
                        event.setId(movie.getInt("id"));
                        event.addToDescription(movie.getString("release_date"), AdditionToDescription.RELEASE_DATE);
                        event.setName(movie.getString("original_title"));
                        event.addToDescription(movie.getString("vote_average"), AdditionToDescription.SCORE);
                        event.setStatus(EventStatus.VIEW);
                        Log.v("MOVIE NAME:" , event.toString());

                    }

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

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
}
