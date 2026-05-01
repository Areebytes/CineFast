package com.example.l23_0656_a1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    public interface OnCancelClickListener {
        void onCancel(Booking booking, int position);
    }

    private ArrayList<Booking> bookings;
    private OnCancelClickListener listener;

    public BookingAdapter(ArrayList<Booking> bookings, OnCancelClickListener listener) {
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking b = bookings.get(position);
        holder.tvMovie.setText(b.getMovieName());
        holder.tvDateTime.setText("Date: " + b.getDateTime());
        holder.tvSeats.setText("Tickets: " + b.getSeats());
        holder.tvPrice.setText("Total: $" + b.getTotalPrice());
        holder.ivPoster.setImageResource(R.drawable.movie);
        holder.btnCancel.setOnClickListener(v -> listener.onCancel(b, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() { return bookings.size(); }

    public void removeItem(int position) {
        bookings.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView  tvMovie, tvDateTime, tvSeats, tvPrice;
        Button    btnCancel;

        ViewHolder(View itemView) {
            super(itemView);
            ivPoster   = itemView.findViewById(R.id.ivBookingPoster);
            tvMovie    = itemView.findViewById(R.id.tvBookingMovie);
            tvDateTime = itemView.findViewById(R.id.tvBookingDateTime);
            tvSeats    = itemView.findViewById(R.id.tvBookingSeats);
            tvPrice    = itemView.findViewById(R.id.tvBookingPrice);
            btnCancel  = itemView.findViewById(R.id.btnCancelBooking);
        }
    }
}