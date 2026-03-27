package com.example.l23_0656_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NowShowingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Dark Knight", "Action / 152 min",
                "https://www.youtube.com/watch?v=EXeTwQWrcwY", R.drawable.logo, false));
        movies.add(new Movie("Inception", "Sci-Fi / 148 min",
                "https://www.youtube.com/watch?v=YoHD9XEInc0", R.drawable.logo, false));
        movies.add(new Movie("Interstellar", "Sci-Fi / 169 min",
                "https://www.youtube.com/watch?v=zSWdZVtXT7E", R.drawable.logo, false));
        movies.add(new Movie("The Shawshank Redemption", "Drama / 142 min",
                "https://www.youtube.com/watch?v=6hB3S9bIaco", R.drawable.logo, false));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieAdapter adapter = new MovieAdapter(getContext(), movies,
                movie -> ((MainActivity) requireActivity()).navigateToSeatSelection(movie));
        recyclerView.setAdapter(adapter);
    }
}