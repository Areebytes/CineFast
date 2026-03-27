package com.example.l23_0656_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String movieName = getArguments().getString("MOVIE_NAME");
        int seatCount = getArguments().getInt("SEAT_COUNT", 0);
        int ticketPrice = getArguments().getInt("TICKET_PRICE", 0);

        ArrayList<Snacks> snackList = new ArrayList<>();
        snackList.add(new Snacks("Popcorn", "Large / Buttered", 8.99, R.drawable.logo));
        snackList.add(new Snacks("Nachos", "With Cheese Dip", 7.99, R.drawable.logo));
        snackList.add(new Snacks("Soft Drink", "Large / Any Flavor", 5.99, R.drawable.logo));
        snackList.add(new Snacks("Candy Mix", "Assorted Candies", 6.99, R.drawable.logo));
        snackList.add(new Snacks("Hot Dog", "With Mustard & Ketchup", 9.99, R.drawable.logo));

        SnacksAdapter adapter = new SnacksAdapter(getContext(), snackList);
        ListView listView = view.findViewById(R.id.listViewSnacks);
        listView.setAdapter(adapter);

        Button btnConfirm = view.findViewById(R.id.btnConfirmSnacks);
        btnConfirm.setOnClickListener(v -> {
            double total = 0;
            ArrayList<String> snackDetails = new ArrayList<>();
            for (Snacks s : snackList) {
                if (s.getQuantity() > 0) {
                    total += s.getQuantity() * s.getPrice();
                    snackDetails.add("x" + s.getQuantity() + " " + s.getName());
                }
            }
            ((MainActivity) requireActivity()).navigateToTicketSummary(
                    movieName, seatCount, ticketPrice, (int) total, snackDetails);
        });
    }
}