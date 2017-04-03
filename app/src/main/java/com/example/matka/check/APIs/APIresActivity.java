package com.example.matka.check.APIs;


import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.matka.check.Category.CategoryActivity;
import com.example.matka.check.R;

import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;


public class APIresActivity extends Activity implements OnItemClickListener , APIListener , SearchView.OnQueryTextListener , Filterable {

    private String[] titles;
    private int [] eventImages;
    private int [] addBtnImages;
    private List<RowItem> rowItems;
    private ListView mylistview;
    private Intent intent;
    private SearchView searchView;

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY = "8a5c1fef1a13c3293e4c069fde43be81";
    private String inputLang = "en-US";
    private final String GET_POPULAR_METHOD = "movie/popular?api_key=";
    private final String SEARCH_METHOD = "search/movie?api_key=";
    private final String LANGUAGE_PREFIX = "&language=en-US";
    private final String PAGE_PREFIX = "&page=1";
    private final String QUERY_PREFIX = "&query=";
    private JsonObjectRequest jsonObjectRequest;
    private final int NUM_OF_RES = 20;
    private final  String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";
    private final String [] IMAGE_SIZE  =  {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
    private ArrayList<Event> popularMoviesList;
    private APIDataSync apidataSync;
    private CategoryName categoryName;
    private Category category;
    private String popUrl;
    //https://api.themoviedb.org/3/movie/popular?api_key=8a5c1fef1a13c3293e4c069fde43be81&language=en-US&page=1
//https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apires);
        popUrl = BASE_URL + GET_POPULAR_METHOD + API_KEY + LANGUAGE_PREFIX + PAGE_PREFIX;
        rowItems = new ArrayList<>();
        categoryName = (CategoryName) getIntent().getSerializableExtra("Category");
        category = new Category(categoryName ,null);
        searchView = (SearchView)findViewById(R.id.search_bar_api);
        searchView.setOnQueryTextListener(this);

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
        rowItems = new ArrayList<>();
        for (int i = 0; i < popularMoviesList.size(); i++) {
            RowItem item = new RowItem(popularMoviesList.get(i).getName(),
                    R.drawable.millennial_explorers,
                    R.drawable.ic_add_circle_outline);
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

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        popUrl = BASE_URL + SEARCH_METHOD  + API_KEY + QUERY_PREFIX + s ;
        Log.v("new url is set" , popUrl);
        apidataSync = new APIDataSync(popUrl,this,this);
        apidataSync.start();
        //Log.v("new url is set" , popUrl);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        popUrl = BASE_URL + SEARCH_METHOD  + API_KEY + QUERY_PREFIX + s ;
        Log.v("new url is set" , popUrl);
        apidataSync = new APIDataSync(popUrl,this,this);
        apidataSync.start();

        /*if((s == null)|| s.equals("")){
            popUrl = BASE_URL + GET_POPULAR_METHOD + API_KEY + LANGUAGE_PREFIX + PAGE_PREFIX;
            apidataSync = new APIDataSync(popUrl,this,this);
            apidataSync.start();
        }**/
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent =  new Intent (this, CategoryActivity.class);
        intent.putExtra("Category",CategoryName.MOVIES);
        startActivity(intent);
    }
}
