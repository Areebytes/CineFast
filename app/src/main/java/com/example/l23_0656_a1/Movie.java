package com.example.l23_0656_a1;

public class Movie {
    private String name;
    private String genre;
    private String trailerUrl;
    private int posterResId;
    private boolean isComingSoon;

    public Movie(String name, String genre, String trailerUrl, int posterResId, boolean isComingSoon) {
        this.name = name;
        this.genre = genre;
        this.trailerUrl = trailerUrl;
        this.posterResId = posterResId;
        this.isComingSoon = isComingSoon;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getTrailerUrl() { return trailerUrl; }
    public int getPosterResId() { return posterResId; }
    public boolean isComingSoon() { return isComingSoon; }

}
