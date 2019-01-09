package com.example.omar.moviedirectory.Activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.moviedirectory.Data.MovieAdapter;
import com.example.omar.moviedirectory.Model.Movie;
import com.example.omar.moviedirectory.R;
import com.example.omar.moviedirectory.Utils.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    List<Movie> movieList;
    RequestQueue queue;
    public static final String FIRST_URL = "http://www.omdbapi.com/?s=";
    public static final String SECOND_URL = "&apikey=4c323ab8";
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
     EditText editText;
     Button searchButton;
     private ShimmerLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        shimmerLayout = (ShimmerLayout)findViewById ( R.id.shimmer );
        recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        queue = Volley.newRequestQueue(MainActivity.this);
        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();
        movieList =new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Log.d ( "Error" , view.toString () );
                showInputDialog ();

            }
        });

        movieList = getPosts(search);
        movieAdapter = new MovieAdapter(movieList , this);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


    }

    public List<Movie> getPosts(String word){
        movieList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FIRST_URL+word+SECOND_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("Search");
                    for (int i =0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(jsonObject.getString("Title"));
                        movie.setType(jsonObject.getString("Type"));
                        movie.setYears(jsonObject.getString("Year"));
                        movie.setImage(jsonObject.getString("Poster"));
                        movie.setId(jsonObject.getString("imdbID"));
                        movieList.add(movie);
                        Log.e("response" , String.valueOf(movie));
                    }
                    shimmerLayout.setVisibility ( View.GONE );
                    shimmerLayout.stopShimmerAnimation ();
                    movieAdapter.notifyDataSetChanged ();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error" , ""+error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);
        return movieList;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            showInputDialog ();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showInputDialog(){
    alertDialogBuilder = new AlertDialog.Builder ( this );
    final View view = getLayoutInflater ().inflate ( R.layout.popup_view , null );
        editText = (EditText)view.findViewById ( R.id.search_edit );
        searchButton = (Button)view.findViewById ( R.id.submit );
        alertDialogBuilder.setView ( view );
        alertDialog = alertDialogBuilder.create ();
        alertDialog.show ();

        searchButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Prefs prefs = new Prefs ( MainActivity.this );
                if (!editText.getText ().toString ().isEmpty ()){
                    String search = editText.getText ().toString ();
                    prefs.setSearch ( search );
                    movieList.clear ();
                    getPosts ( search );
                    movieAdapter.notifyDataSetChanged ();
                    alertDialog.dismiss ();
                }
                else {
                    Snackbar.make(view , "Type correct name please!" , Snackbar.LENGTH_LONG).show();
                }
            }
        } );

    }

    @Override
    protected void onResume() {
        super.onResume ( );
        shimmerLayout.startShimmerAnimation ();
    }

    @Override
    protected void onPause() {
        shimmerLayout.stopShimmerAnimation ();
        super.onPause ( );
    }
}
