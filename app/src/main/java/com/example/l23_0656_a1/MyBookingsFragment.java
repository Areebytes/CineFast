package com.example.l23_0656_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyBookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvNoBookings;
    private BookingAdapter adapter;
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private DatabaseReference dbRef;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView  = view.findViewById(R.id.recyclerBookings);
        tvNoBookings  = view.findViewById(R.id.tvNoBookings);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        userId = user.getUid();
        dbRef  = FirebaseDatabase.getInstance().getReference("bookings").child(userId);

        adapter = new BookingAdapter(bookingList, this::onCancelClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fetchBookings();
    }

    private void fetchBookings() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String bookingId = ds.getKey();
                    String movieName = ds.child("movieName").getValue(String.class);
                    Long   seats     = ds.child("seats").getValue(Long.class);
                    Long   price     = ds.child("totalPrice").getValue(Long.class);
                    String dateTime  = ds.child("dateTime").getValue(String.class);
                    Long   timestamp = ds.child("timestamp").getValue(Long.class);

                    Booking b = new Booking(bookingId,
                            movieName != null ? movieName : "",
                            seats    != null ? seats.intValue()    : 0,
                            price    != null ? price.intValue()    : 0,
                            dateTime != null ? dateTime : "",
                            timestamp!= null ? timestamp : 0L);
                    bookingList.add(b);
                }
                adapter.notifyDataSetChanged();
                tvNoBookings.setVisibility(bookingList.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCancelClicked(Booking booking, int position) {
        // Check if booking is in the future
        if (!isBookingInFuture(booking.getDateTime())) {
            Toast.makeText(getContext(), "Cannot cancel past bookings", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> cancelBooking(booking, position))
                .setNegativeButton("No", null)
                .show();
    }

    private boolean isBookingInFuture(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date bookingDate = sdf.parse(dateTime);
            return bookingDate != null && bookingDate.after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void cancelBooking(Booking booking, int position) {
        dbRef.child(booking.getBookingId()).removeValue()
                .addOnSuccessListener(a -> {
                    adapter.removeItem(position);
                    Toast.makeText(getContext(), "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
                    tvNoBookings.setVisibility(bookingList.isEmpty() ? View.VISIBLE : View.GONE);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to cancel", Toast.LENGTH_SHORT).show());
    }
}