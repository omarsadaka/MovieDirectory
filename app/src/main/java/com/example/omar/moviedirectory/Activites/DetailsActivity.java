package com.example.omar.moviedirectory.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.moviedirectory.Model.Movie;
import com.example.omar.moviedirectory.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class DetailsActivity extends AppCompatActivity {


    Movie movie;
    ImageView movieImage;
    TextView movieTitle;
    TextView movieRealese;
    TextView movieCat;
    TextView movieRate;
    TextView movieRunTime;
    TextView movieDirector;
    TextView movieActor;
    TextView movieWritter;
    TextView moviePlot;
    TextView movieBoxOffice;
    private RequestQueue queue;
    private String movieID;
    public static final String First_URL ="http://www.omdbapi.com/?i=";
    public static final String Second_URL ="&apikey=4c323ab8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        queue = Volley.newRequestQueue ( this );
        movie =(Movie)getIntent().getSerializableExtra("movie");
        movieID = movie.getId();

        setupUI();
        getMovieDetails( movieID);
    }

    private void setupUI() {
         movieImage = (ImageView)findViewById ( R.id.movieImage );
         movieTitle = (TextView)findViewById ( R.id.movieTitle );
         movieRealese = ( TextView)findViewById ( R.id.movieRelease );
         movieCat = (TextView)findViewById ( R.id.movieCat );
         movieRate = (TextView)findViewById ( R.id.movieRate );
         movieRunTime = (TextView)findViewById ( R.id.runTime );
         movieDirector = (TextView)findViewById ( R.id.auther );
         movieActor = (TextView)findViewById ( R.id.actors );
         movieWritter = (TextView)findViewById ( R.id.writer );
         moviePlot = (TextView)findViewById ( R.id.plot );
         movieBoxOffice = (TextView)findViewById ( R.id.boxOffice );




    }
    private void getMovieDetails(String ID) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest ( Request.Method.GET, First_URL + ID +Second_URL, new Response.Listener<JSONObject> ( ) {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has ( "Ratings" )){
                    try {
                        JSONArray jsonArray = response.getJSONArray ( "Ratings" );
                        String source = null;
                        String value = null;
                        if (jsonArray.length ()>0){
                            JSONObject jsonObject = jsonArray.getJSONObject ( jsonArray.length ()-1 );
                            source = jsonObject.getString ( "Source" );
                            value = jsonObject.getString ( "Value" );
                            movieRate.setText ( source + " : " + value );
                        }
                        else {
                            movieRate.setText ( "Ratings: N/A" );
                        }
                        movieTitle.setText ( response.getString ( "Title" ) );
                        movieRealese.setText ( "Released: " + response.getString ( "Released" ) );
                        movieDirector.setText ( "Director: " + response.getString ( "Director" ) );
                        movieWritter.setText ( "Writer: " + response.getString ( "Writer" ) );
                        moviePlot.setText ( "Plot: " + response.getString ( "Plot" ) );
                        movieRunTime.setText ( "Runtime: " + response.getString ( "Runtime" ) );
                        movieActor.setText ( "Actor: " + response.getString ( "Actors" ) );

                        Picasso.get ().load ( response.getString ( "Poster" ) ).into ( movieImage );
                        movieBoxOffice.setText ( "Box Office: " + response.getString ( "BoxOffice" ) );




                    } catch (JSONException e) {
                        e.printStackTrace ( );
                    }
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d ( "Error:" , error.getMessage () );

            }
        } );
            queue.add ( jsonObjectRequest );
    }


}
