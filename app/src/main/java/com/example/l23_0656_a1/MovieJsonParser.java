package com.example.l23_0656_a1;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MovieJsonParser {

    public static ArrayList<Movie> loadMovies(Context context, String key) {
        ArrayList<Movie> list = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject root = new JSONObject(json);
            JSONArray arr   = root.getJSONArray(key);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name        = obj.getString("name");
                String genre       = obj.getString("genre");
                String trailerUrl  = obj.getString("trailerUrl");
                boolean comingSoon = obj.getBoolean("isComingSoon");
                list.add(new Movie(name, genre, trailerUrl, R.drawable.movie, comingSoon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}