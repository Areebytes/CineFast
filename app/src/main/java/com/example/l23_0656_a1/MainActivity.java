package com.example.l23_0656_a1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "cinefast_session_pref_v3";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        Toast.makeText(this, "CineFAST", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout   = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment(), false);
            } else if (id == R.id.nav_my_bookings) {
                loadFragment(new MyBookingsFragment(), false);
            } else if (id == R.id.nav_logout) {
                logout();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().clear().apply();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
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

    // Kept for backward compatibility with HomeFragment menu
    public void showLastBooking() {
        SharedPreferences prefs = getSharedPreferences("CineFastPrefs", MODE_PRIVATE);
        String movie = prefs.getString("LAST_MOVIE", null);
        if (movie == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Last Booking").setMessage("No previous booking found.")
                    .setPositiveButton("OK", null).show();
        } else {
            int seats = prefs.getInt("LAST_SEATS", 0);
            int price = prefs.getInt("LAST_PRICE", 0);
            new AlertDialog.Builder(this)
                    .setTitle("Last Booking")
                    .setMessage("Movie: " + movie + "\nSeats: " + seats + "\nTotal: $" + price)
                    .setPositiveButton("OK", null).show();
        }
    }

    public void saveBooking(String movieName, int seatCount, int totalPrice) {
        getSharedPreferences("CineFastPrefs", MODE_PRIVATE).edit()
                .putString("LAST_MOVIE", movieName)
                .putInt("LAST_SEATS", seatCount)
                .putInt("LAST_PRICE", totalPrice)
                .apply();
    }
}