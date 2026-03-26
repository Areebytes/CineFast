package com.example.l23_0656_a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SnacksActivity extends AppCompatActivity {

    private TextView txtPopcornQty, txtNachosQty, txtDrinkQty, txtCandyQty;
    private Button btnConfirm;
    private int popcornQty = 0, nachosQty = 0, drinkQty = 0, candyQty = 0;
    private final double POPCORN_PRICE = 8.99;
    private final double NACHOS_PRICE = 7.99;
    private final double DRINK_PRICE = 5.99;
    private final double CANDY_PRICE = 6.99;
    private String movieName;
    private int seatCount;
    private int ticketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        movieName = getIntent().getStringExtra("MOVIE_NAME");
        seatCount = getIntent().getIntExtra("SEAT_COUNT", 0);
        ticketPrice = getIntent().getIntExtra("TICKET_PRICE", 0);

        txtPopcornQty = findViewById(R.id.txtPopcornQty);
        txtNachosQty = findViewById(R.id.txtNachosQty);
        txtDrinkQty = findViewById(R.id.txtDrinkQty);
        txtCandyQty = findViewById(R.id.txtCandyQty);
        btnConfirm = findViewById(R.id.btnConfirm);

        Button btnPopcornPlus = findViewById(R.id.btnPopcornPlus);
        Button btnPopcornMinus = findViewById(R.id.btnPopcornMinus);
        Button btnNachosPlus = findViewById(R.id.btnNachosPlus);
        Button btnNachosMinus = findViewById(R.id.btnNachosMinus);
        Button btnDrinkPlus = findViewById(R.id.btnDrinkPlus);
        Button btnDrinkMinus = findViewById(R.id.btnDrinkMinus);
        Button btnCandyPlus = findViewById(R.id.btnCandyPlus);
        Button btnCandyMinus = findViewById(R.id.btnCandyMinus);

        btnPopcornPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popcornQty++;
                txtPopcornQty.setText(String.valueOf(popcornQty));
            }
        });

        btnPopcornMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popcornQty > 0) {
                    popcornQty--;
                    txtPopcornQty.setText(String.valueOf(popcornQty));
                }
            }
        });

        btnNachosPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nachosQty++;
                txtNachosQty.setText(String.valueOf(nachosQty));
            }
        });

        btnNachosMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nachosQty > 0) {
                    nachosQty--;
                    txtNachosQty.setText(String.valueOf(nachosQty));
                }
            }
        });

        btnDrinkPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkQty++;
                txtDrinkQty.setText(String.valueOf(drinkQty));
            }
        });

        btnDrinkMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drinkQty > 0) {
                    drinkQty--;
                    txtDrinkQty.setText(String.valueOf(drinkQty));
                }
            }
        });

        btnCandyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                candyQty++;
                txtCandyQty.setText(String.valueOf(candyQty));
            }
        });

        btnCandyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (candyQty > 0) {
                    candyQty--;
                    txtCandyQty.setText(String.valueOf(candyQty));
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double snacksTotal = popcornQty * POPCORN_PRICE +
                        nachosQty * NACHOS_PRICE +
                        drinkQty * DRINK_PRICE +
                        candyQty * CANDY_PRICE;

                ArrayList<String> snackDetails = new ArrayList<>();
                if (popcornQty > 0) snackDetails.add("x" + popcornQty + " Popcorn");
                if (nachosQty > 0) snackDetails.add("x" + nachosQty + " Nachos");
                if (drinkQty > 0) snackDetails.add("x" + drinkQty + " Soft Drink");
                if (candyQty > 0) snackDetails.add("x" + candyQty + " Candy Mix");

                Intent intent = new Intent(SnacksActivity.this, TicketSummaryActivity.class);
                intent.putExtra("MOVIE_NAME", movieName);
                intent.putExtra("SEAT_COUNT", seatCount);
                intent.putExtra("TICKET_PRICE", ticketPrice);
                intent.putExtra("SNACKS_TOTAL", (int) snacksTotal);
                intent.putStringArrayListExtra("SNACK_DETAILS", snackDetails);
                startActivity(intent);
            }
        });
    }
}