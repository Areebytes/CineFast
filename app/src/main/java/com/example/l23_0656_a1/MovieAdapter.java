package com.example.l23_0656_a1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;
    private OnBookClickListener bookClickListener;

    public interface OnBookClickListener {
        void onBookClick(Movie movie);
    }

    public MovieAdapter(Context context, ArrayList<Movie> movieList, OnBookClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.bookClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.txtName.setText(movie.getName());
        holder.txtGenre.setText(movie.getGenre());
        holder.imgPoster.setImageResource(movie.getPosterResId());

        holder.btnTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
            context.startActivity(intent);
        });

        holder.btnBook.setOnClickListener(v -> bookClickListener.onBookClick(movie));
    }

    @Override
    public int getItemCount() { return movieList.size(); }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtName, txtGenre;
        Button btnTrailer, btnBook;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgMoviePoster);
            txtName = itemView.findViewById(R.id.txtMovieName);
            txtGenre = itemView.findViewById(R.id.txtMovieGenre);
            btnTrailer = itemView.findViewById(R.id.btnMovieTrailer);
            btnBook = itemView.findViewById(R.id.btnMovieBook);
        }
    }
}