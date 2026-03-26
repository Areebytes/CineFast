package com.example.l23_0656_a1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SeatSelectionActivity extends AppCompatActivity {

    private TextView txtSelectedMovie;
    private Button btnProceedSnacks, btnBookSeats;

    private Button[][] seats = new Button[5][8];
    private boolean[][] selected = new boolean[5][8];
    private boolean[][] booked = new boolean[5][8];

    private final int SEAT_PRICE = 10;
    private int selectedCount = 0;
    private String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        movieName = getIntent().getStringExtra("MOVIE_NAME");
        txtSelectedMovie = findViewById(R.id.txtSelectedMovie);
        txtSelectedMovie.setText(movieName);

        btnProceedSnacks = findViewById(R.id.btnProceedSnacks);
        btnBookSeats = findViewById(R.id.btnBookSeats);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                String id = "seat_" + i + j;
                int resId = getResources().getIdentifier(id, "id", getPackageName());
                seats[i][j] = findViewById(resId);
                final int row = i, col = j;
                seats[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleSeat(row, col);
                    }
                });
            }
        }

        booked[0][2] = true;
        booked[1][5] = true;
        booked[2][3] = true;
        booked[4][0] = true;

        updateSeatColors();
        updateUI();

        btnProceedSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatSelectionActivity.this, SnacksActivity.class);
                intent.putExtra("MOVIE_NAME", movieName);
                intent.putExtra("SEAT_COUNT", selectedCount);
                intent.putExtra("TICKET_PRICE", selectedCount * SEAT_PRICE);
                startActivity(intent);
            }
        });

        btnBookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatSelectionActivity.this, TicketSummaryActivity.class);
                intent.putExtra("MOVIE_NAME", movieName);
                intent.putExtra("SEAT_COUNT", selectedCount);
                intent.putExtra("TICKET_PRICE", selectedCount * SEAT_PRICE);
                intent.putExtra("SNACKS_TOTAL", 0);
                startActivity(intent);
            }
        });
    }

    private void toggleSeat(int row, int col) {
        if (booked[row][col]) return;

        selected[row][col] = !selected[row][col];
        if (selected[row][col]) {
            selectedCount++;
        } else {
            selectedCount--;
        }
        updateSeatColors();
        updateUI();
    }

    private void updateSeatColors() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                Button seat = seats[i][j];
                if (booked[i][j]) {
                    seat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                } else if (selected[i][j]) {
                    seat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                } else {
                    seat.setBackgroundTintList(ColorStateList.valueOf(0xFF333333));
                }
            }
        }
    }

    private void updateUI() {
        btnProceedSnacks.setEnabled(selectedCount > 0);
    }
}