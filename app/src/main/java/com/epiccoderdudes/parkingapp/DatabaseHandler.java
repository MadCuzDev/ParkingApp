package com.epiccoderdudes.parkingapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "parking_r_us", TABLE_NAME = "parking";
    private static final int DB_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(location varchar(255), date varchar(255), duration varchar(255), plate varchar(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void addParking(String location, String date, String duration, String plate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("location", location);
        contentValues.put("date", date);
        contentValues.put("duration", duration);
        contentValues.put("plate", plate);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<String> getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> history = new ArrayList<>();

        Cursor cursor;

        cursor = db.rawQuery("SELECT location, date, duration, plate FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        history.add(cursor.getString(0) + "\n" +  cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + "\n\n");
        while (!cursor.isLast()) {
            cursor.moveToNext();
            history.add(cursor.getString(0) + "\n" +  cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + "\n\n");
        }

        history.add(cursor.getString(0) + "\n" +  cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + "\n\n");


        cursor.close();
        return history;
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

}

