package com.example.l23_0656_a1;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TicketSummaryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String movieName = getArguments().getString("MOVIE_NAME");
        int seatCount = getArguments().getInt("SEAT_COUNT", 0);
        int ticketPrice = getArguments().getInt("TICKET_PRICE", 0);
        int snacksTotal = getArguments().getInt("SNACKS_TOTAL", 0);
        ArrayList<String> snackDetails = getArguments().getStringArrayList("SNACK_DETAILS");

        view.<TextView>findViewById(R.id.txtMovieName).setText(movieName);
        view.<TextView>findViewById(R.id.txtSeatInfo).setText("Seats: " + seatCount);
        view.<TextView>findViewById(R.id.txtTicketPrice).setText("Ticket Total: $" + ticketPrice);
        view.<TextView>findViewById(R.id.txtSnacksTotal).setText("Snacks Total: $" + snacksTotal);

        int grandTotal = ticketPrice + snacksTotal;
        view.<TextView>findViewById(R.id.txtGrandTotal).setText("$" + grandTotal + " USD");

        LinearLayout snacksContainer = view.findViewById(R.id.snacksContainer);
        if (snackDetails != null && !snackDetails.isEmpty()) {
            for (String snack : snackDetails) {
                TextView tv = new TextView(getContext());
                tv.setText(snack);
                tv.setTextColor(0xFF888888);
                tv.setTextSize(14);
                snacksContainer.addView(tv);
            }
        } else {
            TextView tv = new TextView(getContext());
            tv.setText("No snacks selected");
            tv.setTextColor(0xFF888888);
            snacksContainer.addView(tv);
        }

        // Save to firebase
        saveBookingToFirebase(movieName, seatCount, grandTotal);

        Button btnSendTicket = view.findViewById(R.id.btnSendTicket);
        btnSendTicket.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Movie: " + movieName + "\nSeats: " + seatCount +
                            "\nTickets: $" + ticketPrice + "\nSnacks: $" + snacksTotal +
                            "\nTotal: $" + grandTotal);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Your CineFAST Ticket");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }

    private void saveBookingToFirebase(String movieName, int seatCount, int totalPrice) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        String userId    = user.getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        Map<String, Object> booking = new HashMap<>();
        booking.put("movieName",  movieName);
        booking.put("seats",      seatCount);
        booking.put("totalPrice", totalPrice);
        booking.put("dateTime",   dateTime);
        booking.put("timestamp",  System.currentTimeMillis());

        String bookingId = dbRef.child("bookings").child(userId).push().getKey();
        dbRef.child("bookings").child(userId).child(bookingId).setValue(booking)
                .addOnSuccessListener(a ->
                        Toast.makeText(getContext(), "Booking saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to save booking", Toast.LENGTH_SHORT).show());
    }
}