package com.example.l23_0656_a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TicketSummaryActivity extends AppCompatActivity {

    private TextView txtMovieName, txtSeatInfo, txtTicketPrice, txtSnacksTotal, txtGrandTotal;
    private LinearLayout snacksContainer;
    private Button btnSendTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary);

        String movieName = getIntent().getStringExtra("MOVIE_NAME");
        int seatCount = getIntent().getIntExtra("SEAT_COUNT", 0);
        int ticketPrice = getIntent().getIntExtra("TICKET_PRICE", 0);
        int snacksTotal = getIntent().getIntExtra("SNACKS_TOTAL", 0);
        ArrayList<String> snackDetails = getIntent().getStringArrayListExtra("SNACK_DETAILS");

        txtMovieName = findViewById(R.id.txtMovieName);
        txtSeatInfo = findViewById(R.id.txtSeatInfo);
        txtTicketPrice = findViewById(R.id.txtTicketPrice);
        txtSnacksTotal = findViewById(R.id.txtSnacksTotal);
        txtGrandTotal = findViewById(R.id.txtGrandTotal);
        snacksContainer = findViewById(R.id.snacksContainer);
        btnSendTicket = findViewById(R.id.btnSendTicket);

        txtMovieName.setText(movieName);
        txtSeatInfo.setText("Selected Seats: " + seatCount);
        txtTicketPrice.setText("Ticket Total: $" + ticketPrice);

        if (snackDetails != null && !snackDetails.isEmpty()) {
            for (String snack : snackDetails) {
                TextView tv = new TextView(this);
                tv.setText(snack);
                tv.setTextSize(14);
                snacksContainer.addView(tv);
            }
            txtSnacksTotal.setText("Snacks Total: $" + snacksTotal);
        } else {
            TextView tv = new TextView(this);
            tv.setText("No snacks selected");
            tv.setTextSize(14);
            snacksContainer.addView(tv);
            txtSnacksTotal.setText("Snacks Total: $0");
        }

        int grandTotal = ticketPrice + snacksTotal;
        txtGrandTotal.setText("TOTAL: $" + grandTotal);

        btnSendTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Movie: " + movieName +
                        "\nSeats: " + seatCount +
                        "\nTicket Price: $" + ticketPrice +
                        "\nSnacks Total: $" + snacksTotal +
                        "\nGrand Total: $" + grandTotal;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Your CineFAST Ticket");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }
}