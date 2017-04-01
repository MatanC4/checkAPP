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
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.matka.check.Event.EventActivity;
import com.example.matka.check.Event.EventInfoActivity;
import com.example.matka.check.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import bl.entities.AdditionToDescription;
import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;


public class APIresActivity extends Activity implements OnItemClickListener , APIListener {

    private String[] titles;
    private int [] eventImages;
    private int [] addBtnImages;
    private List<RowItem> rowItems;
    ListView mylistview;
    Intent intent;

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
    private APIDataSync apidataSync;
    private CategoryName categoryName;
    private Category category;

    //https://api.themoviedb.org/3/movie/popular?api_key=8a5c1fef1a13c3293e4c069fde43be81&language=en-US&page=1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apires);
        final String popUrl = BASE_URL + getPopularMethod + API_KEY + languagePrefix + pagePrefix;
        rowItems = new ArrayList<>();
        categoryName = (CategoryName) getIntent().getSerializableExtra("Category");
        category = new Category(categoryName ,null);
        apidataSync = new APIDataSync(popUrl,this,this);
        apidataSync.start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String title = rowItems.get(position).getTitle();
        Toast.makeText(getApplicationContext(), "" + title,
                Toast.LENGTH_SHORT).show();
    }



    @Override
    public void passArrayList(ArrayList<Event> popularMoviesList) {
        //Log.v("FROM_API_RES" , popularMoviesList.toString());
        for (int i = 0; i < popularMoviesList.size(); i++) {
            RowItem item = new RowItem(popularMoviesList.get(i).getName(),
                    R.drawable.millennial_explorers,
                    R.drawable.plus_1);
            rowItems.add(item);
            popularMoviesList.get(i).setCategory(category);
        }
        //Log.v("before Adapter", rowItems.toString());
        mylistview = (ListView) findViewById(R.id.list_for_api_res);
        CustomAdapter adapter = new CustomAdapter(this, rowItems , popularMoviesList);
        mylistview.setAdapter(adapter);

                mylistview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });


    }
}
