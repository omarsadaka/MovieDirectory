package com.example.omar.moviedirectory.Data;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.moviedirectory.Activites.DetailsActivity;
import com.example.omar.moviedirectory.Model.Movie;
import com.example.omar.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by OMAR on 10/22/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<Movie> movieList;
    Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_row,parent,false);
        return new ViewHolder(view  ,context);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
       Movie movie = movieList.get(position);
       String poster = movie.getImage();

       holder.title.setText(movie.getTitle());
       holder.year.setText(movie.getYears());
       holder.type.setText(movie.getType());
        Picasso.get().load(poster).placeholder(R.drawable.ic_launcher_background).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView poster;
        TextView title;
        TextView year;
        TextView type;

        public ViewHolder(View itemView , final Context ctx) {
            super(itemView);
            context = ctx;
            poster = (ImageView)itemView.findViewById(R.id.movieImage);
            title= (TextView)itemView.findViewById(R.id.movieTitle);
            year = (TextView)itemView.findViewById(R.id.movieRelease);
            type = (TextView)itemView.findViewById(R.id.movieCat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Movie movie = movieList.get(getAdapterPosition());
                    Intent intent = new Intent(context , DetailsActivity.class);
                    intent.putExtra ( "movie" , movie );
                    ctx.startActivity(intent);

                }
            });


        }


    }
}
