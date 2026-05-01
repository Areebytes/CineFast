package com.example.l23_0656_a1;

public class Booking {
    private String bookingId;
    private String movieName;
    private int seats;
    private int totalPrice;
    private String dateTime;
    private long timestamp;

    public Booking() {}

    public Booking(String bookingId, String movieName, int seats, int totalPrice,
                   String dateTime, long timestamp) {
        this.bookingId  = bookingId;
        this.movieName  = movieName;
        this.seats      = seats;
        this.totalPrice = totalPrice;
        this.dateTime   = dateTime;
        this.timestamp  = timestamp;
    }

    public String getBookingId()  { return bookingId; }
    public String getMovieName()  { return movieName; }
    public int    getSeats()      { return seats; }
    public int    getTotalPrice() { return totalPrice; }
    public String getDateTime()   { return dateTime; }
    public long   getTimestamp()  { return timestamp; }

    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
}