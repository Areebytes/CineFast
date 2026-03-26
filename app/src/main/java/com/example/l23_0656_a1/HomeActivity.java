package com.example.l23_0656_a1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupMovie1();
        setupMovie2();
        setupMovie3();
        setupMovie4();

        Button btnToday = findViewById(R.id.btnToday);
        Button btnTomorrow = findViewById(R.id.btnTomorrow);
    }

    private void setupMovie1() {
        Button btnTrailer = findViewById(R.id.btnTrailer1);
        Button btnBook = findViewById(R.id.btnBook1);

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=EXeTwQWrcwY"));
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
                intent.putExtra("MOVIE_NAME", "The Dark Knight");
                startActivity(intent);
            }
        });
    }

    private void setupMovie2() {
        Button btnTrailer = findViewById(R.id.btnTrailer2);
        Button btnBook = findViewById(R.id.btnBook2);

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=YoHD9XEInc0"));
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
                intent.putExtra("MOVIE_NAME", "Inception");
                startActivity(intent);
            }
        });
    }

    private void setupMovie3() {
        Button btnTrailer = findViewById(R.id.btnTrailer3);
        Button btnBook = findViewById(R.id.btnBook3);

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=zSWdZVtXT7E"));
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
                intent.putExtra("MOVIE_NAME", "Interstellar");
                startActivity(intent);
            }
        });
    }

    private void setupMovie4() {
        Button btnTrailer = findViewById(R.id.btnTrailer4);
        Button btnBook = findViewById(R.id.btnBook4);

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6hB3S9bIaco"));
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
                intent.putExtra("MOVIE_NAME", "The Shawshank Redemption");
                startActivity(intent);
            }
        });
    }
}