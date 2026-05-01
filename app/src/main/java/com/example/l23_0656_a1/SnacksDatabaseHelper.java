package com.example.l23_0656_a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class SnacksDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "cinefast.db";
    private static final int    DB_VERSION = 1;
    private static final String TABLE_NAME = "snacks";

    public SnacksDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "image INTEGER)";
        db.execSQL(createTable);
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        insertSnack(db, "Popcorn",    8.99, R.drawable.snacks);
        insertSnack(db, "Nachos",     7.99, R.drawable.snacks);
        insertSnack(db, "Soft Drink", 5.99, R.drawable.snacks);
        insertSnack(db, "Candy Mix",  6.99, R.drawable.snacks);
        insertSnack(db, "Hot Dog",    9.99, R.drawable.snacks);
    }

    private void insertSnack(SQLiteDatabase db, String name, double price, int image) {
        ContentValues cv = new ContentValues();
        cv.put("name",  name);
        cv.put("price", price);
        cv.put("image", image);
        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<Snacks> getAllSnacks() {
        ArrayList<Snacks> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name  = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int    image = cursor.getInt(cursor.getColumnIndexOrThrow("image"));
            // Reuse existing Snacks model — subtitle is not stored, use name
            list.add(new Snacks(name, "", price, image));
        }
        cursor.close();
        return list;
    }
}