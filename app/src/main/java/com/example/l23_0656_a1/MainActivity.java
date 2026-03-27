package com.example.l23_0656_a1;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void navigateToSeatSelection(Movie movie) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString("MOVIE_NAME", movie.getName());
        args.putString("TRAILER_URL", movie.getTrailerUrl());
        args.putBoolean("IS_COMING_SOON", movie.isComingSoon());
        fragment.setArguments(args);
        loadFragment(fragment, true);
    }

    public void navigateToSnacks(String movieName, int seatCount, int ticketPrice) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putString("MOVIE_NAME", movieName);
        args.putInt("SEAT_COUNT", seatCount);
        args.putInt("TICKET_PRICE", ticketPrice);
        fragment.setArguments(args);
        loadFragment(fragment, true);
    }

    public void navigateToTicketSummary(String movieName, int seatCount,
                                        int ticketPrice, int snacksTotal,
                                        ArrayList<String> snackDetails) {
        TicketSummaryFragment fragment = new TicketSummaryFragment();
        Bundle args = new Bundle();
        args.putString("MOVIE_NAME", movieName);
        args.putInt("SEAT_COUNT", seatCount);
        args.putInt("TICKET_PRICE", ticketPrice);
        args.putInt("SNACKS_TOTAL", snacksTotal);
        args.putStringArrayList("SNACK_DETAILS", snackDetails);
        fragment.setArguments(args);
        loadFragment(fragment, true);
    }

    public void saveBooking(String movieName, int seatCount, int totalPrice) {
        SharedPreferences prefs = getSharedPreferences("CineFastPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LAST_MOVIE", movieName);
        editor.putInt("LAST_SEATS", seatCount);
        editor.putInt("LAST_PRICE", totalPrice);
        editor.apply();
    }

    public void showLastBooking() {
        SharedPreferences prefs = getSharedPreferences("CineFastPrefs", MODE_PRIVATE);
        String movie = prefs.getString("LAST_MOVIE", null);
        if (movie == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Last Booking")
                    .setMessage("No previous booking found.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            int seats = prefs.getInt("LAST_SEATS", 0);
            int price = prefs.getInt("LAST_PRICE", 0);
            String msg = "Movie: " + movie + "\nSeats: " + seats + "\nTotal Price: $" + price;
            new AlertDialog.Builder(this)
                    .setTitle("Last Booking")
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}