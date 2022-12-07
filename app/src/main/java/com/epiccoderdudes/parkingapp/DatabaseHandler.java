package com.epiccoderdudes.parkingapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    /*
    public String getResults(boolean pastMinute) {
        SQLiteDatabase db = this.getReadableDatabase();

        String win;
        String loss;
        String tie;

        Cursor cursor;
        if (!pastMinute) {

            cursor = db.rawQuery("SELECT COUNT(*) FROM stats WHERE result = ?", new String[]{"win"});
            cursor.moveToFirst();
            win = cursor.getString(0);

            cursor = db.rawQuery("SELECT COUNT(*) FROM stats WHERE result = ?", new String[]{"loss"});
            cursor.moveToFirst();
            loss = cursor.getString(0);

            cursor = db.rawQuery("SELECT COUNT(*) FROM stats WHERE result = ?", new String[]{"tie"});

        } else {
            cursor = db.rawQuery("SELECT COUNT(*) FROM stats WHERE result = ? AND timestamp >= ?",
                    new String[]{"win", Integer.toString(Math.round(System.currentTimeMillis() / 1000f) - 60)});
            cursor.moveToFirst();
            win = cursor.getString(0);

            cursor = db.rawQuery("SELECT COUNT(*) FROM stats WHERE result = ? AND timestamp >= ?",
                    new String[]{"loss", Integer.toString(Math.round(System.currentTimeMillis() / 1000f)  - 60)});
            cursor.moveToFirst();
            loss = cursor.getString(0);

            cursor = db.rawQuery("SELECT COUNT(*) FROM stats WHERE result = ? AND timestamp >= ?",
                    new String[]{"tie", Integer.toString(Math.round(System.currentTimeMillis() / 1000f)  - 60)});

        }
        cursor.moveToFirst();
        tie = cursor.getString(0);
        cursor.close();
        return win + "-" + loss + "-" + tie;
    }
    */

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

}

