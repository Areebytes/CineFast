package com.example.l23_0656_a1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SeatSelectionFragment extends Fragment {

    private Button[][] seats = new Button[5][8];
    private boolean[][] selected = new boolean[5][8];
    private boolean[][] booked = new boolean[5][8];
    private final int SEAT_PRICE = 10;
    private int selectedCount = 0;
    private String movieName;
    private String trailerUrl;
    private boolean isComingSoon;
    private Button btnProceedSnacks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        movieName = args.getString("MOVIE_NAME");
        trailerUrl = args.getString("TRAILER_URL");
        isComingSoon = args.getBoolean("IS_COMING_SOON", false);

        TextView txtMovieName = view.findViewById(R.id.txtSelectedMovie);
        txtMovieName.setText(movieName);

        btnProceedSnacks = view.findViewById(R.id.btnProceedSnacks);
        Button btnBookSeats = view.findViewById(R.id.btnBookSeats);
        Button btnComingSoon = view.findViewById(R.id.btnComingSoon);
        Button btnWatchTrailer = view.findViewById(R.id.btnWatchTrailer);

        // find the two button row containers
        LinearLayout nowShowingButtons = view.findViewById(R.id.layoutNowShowingButtons);
        LinearLayout comingSoonButtons = view.findViewById(R.id.layoutComingSoonButtons);

        // load seat buttons
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                String id = "seat_" + i + j;
                int resId = getResources().getIdentifier(id, "id", requireActivity().getPackageName());
                seats[i][j] = view.findViewById(resId);
                final int row = i, col = j;
                seats[i][j].setOnClickListener(v -> {
                    if (!isComingSoon) toggleSeat(row, col, view);
                });
            }
        }

        booked[0][2] = true;
        booked[1][5] = true;
        booked[2][3] = true;
        booked[4][0] = true;
        updateSeatColors();

        if (isComingSoon) {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 8; j++)
                    seats[i][j].setEnabled(false);

            nowShowingButtons.setVisibility(View.GONE);
            comingSoonButtons.setVisibility(View.VISIBLE);
            btnComingSoon.setEnabled(false);

            btnWatchTrailer.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                startActivity(intent);
            });
        } else {
            nowShowingButtons.setVisibility(View.VISIBLE);
            comingSoonButtons.setVisibility(View.GONE);

            btnProceedSnacks.setOnClickListener(v ->
                    ((MainActivity) requireActivity()).navigateToSnacks(
                            movieName, selectedCount, selectedCount * SEAT_PRICE));

            btnBookSeats.setOnClickListener(v -> {
                if (selectedCount == 0) {
                    Toast.makeText(getContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).navigateToTicketSummary(
                        movieName, selectedCount, selectedCount * SEAT_PRICE, 0, new ArrayList<>());
            });
        }
    }

    private void toggleSeat(int row, int col, View view) {
        if (booked[row][col]) return;
        selected[row][col] = !selected[row][col];
        selectedCount += selected[row][col] ? 1 : -1;
        updateSeatColors();
        btnProceedSnacks.setEnabled(selectedCount > 0);
    }

    private void updateSeatColors() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                if (booked[i][j]) {
                    seats[i][j].setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.red)));
                } else if (selected[i][j]) {
                    seats[i][j].setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.green)));
                } else {
                    seats[i][j].setBackgroundTintList(ColorStateList.valueOf(0xFF555555));
                }
            }
        }
    }
}