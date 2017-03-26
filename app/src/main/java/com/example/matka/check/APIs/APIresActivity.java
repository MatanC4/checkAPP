package com.example.matka.check.APIs;


import android.app.Activity;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.matka.check.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import bl.entities.AdditionToDescription;
import bl.entities.Event;


public class APIresActivity extends Activity implements OnItemClickListener , APIListener {

    private String[] titles;
    private int [] eventImages;
    private int [] addBtnImages;
    List<RowItem> rowItems;
    ListView mylistview;

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
    private ArrayList<Event> popularMoviesList;
    private APIDataSync apidataSync;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apires);
        final String popUrl = BASE_URL + getPopularMethod + API_KEY + languagePrefix + pagePrefix;
        rowItems = new ArrayList<>();
        apidataSync = new APIDataSync(popUrl,this,this);
        apidataSync.start();
        //popularMoviesList = new ArrayList<>();
        //getPopularMovies(popUrl);

       // titles = new String[]{"Title 1","Title2", "Title 3","Title 1","Title2", "Title 3","Title 1","Title2"};



       /* eventImages =  new  int[]{
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers,
                R.drawable.millennial_explorers
        };**/

       /* addBtnImages = new int[] {
                R.drawable.plus_1,
                R.drawable.plus_1,
                R.drawable.plus_1,
                R.drawable.plus_1,
                R.drawable.plus_1,
                R.drawable.plus_1,
                R.drawable.plus_1,
                R.drawable.plus_1
        };**/



      /**  new Thread(new Runnable() {
            @Override
            public void run() {
                postAPI();
            }
        }).start(); **/





    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String title = rowItems.get(position).getTitle();
        Toast.makeText(getApplicationContext(), "" + title,
                Toast.LENGTH_SHORT).show();
    }



    private void getPopularMovies(String popUrl) {

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
                        popularMoviesList.add(event);
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



    @Override
    public void passArrayList(ArrayList<Event> popularMoviesList) {
        for (int i = 0; i < popularMoviesList.size(); i++) {
            RowItem item = new RowItem(popularMoviesList.get(i).getName(),
                    R.drawable.millennial_explorers,
                    R.drawable.plus_1);
            rowItems.add(item);
            Log.v("suspcted Loop", item.toString());
           // Picasso.with(this).load(popularMoviesList.get(i).getImageURL()).into();
        }
        mylistview = (ListView) findViewById(R.id.list_for_api_res);
        CustomAdapter adapter = new CustomAdapter(this, rowItems , popularMoviesList);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(this);


    }
}
