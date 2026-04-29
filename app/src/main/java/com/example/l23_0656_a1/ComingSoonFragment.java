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

public class ComingSoonFragment extends Fragment {

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
        movies.add(new Movie("Dune: Part Three", "Sci-Fi / TBD",
                "https://www.youtube.com/watch?v=n9xhJrPXop4", R.drawable.movie, true));
        movies.add(new Movie("Avatar 3", "Fantasy / TBD",
                "https://www.youtube.com/watch?v=d9MyW72ELq0", R.drawable.movie, true));
        movies.add(new Movie("Mission Impossible 8", "Action / TBD",
                "https://www.youtube.com/watch?v=avz06PDqDbM", R.drawable.movie, true));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieAdapter adapter = new MovieAdapter(getContext(), movies,
                movie -> ((MainActivity) requireActivity()).navigateToSeatSelection(movie));
        recyclerView.setAdapter(adapter);
    }
}