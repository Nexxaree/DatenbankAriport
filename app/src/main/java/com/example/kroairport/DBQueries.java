package com.example.kroairport;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBQueries {
    Context context;
    public SQLiteDatabase database;
    DBHelper dbHelper;

    public DBQueries(Context context) {
        this.context = context;
    }

    public DBQueries open() {
        dbHelper = new DBHelper(context, MainActivity.db, null, 1);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    @SuppressLint("Range")
    public ArrayList<String> getDetail() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor;
        database = dbHelper.getReadableDatabase();
        cursor = database.rawQuery("SELECT * from fluggast", null);
        list.clear();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex("F_Vorname")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_Nachname")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_Telefon")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_email")));
                    list.add("\n");
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<String> searchname(String name) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor;
        database = dbHelper.getReadableDatabase();
        cursor = database.rawQuery("SELECT * from fluggast " +
                "join bordkarte on fluggast.Fluggast_id = bordkarte.Fluggast_id " +
                "join flug on bordkarte.Flug_id = flug.Flug_id where F_Vorname = " + '"' + name + '"', null);
        list.clear();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex("F_Vorname")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_Nachname")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_Telefon")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_email")));
                    list.add(cursor.getString(cursor.getColumnIndex("Flug_Nr")));
                    list.add("\n");
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }
        return list;
    }

    /*@SuppressLint("Range")
    public ArrayList<String> searchflug(String flugnummer) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor;
        database = dbHelper.getReadableDatabase();
        cursor = database.rawQuery("SELECT * from fluggast " +
                "join bordkarte on fluggast.Fluggast_id = bordkarte.Fluggast_id " +
                "join flug on bordkarte.Flug_id = flug.Flug_id where F_Vorname = " + '"' + name + '"', null);
        list.clear();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex("F_Vorname")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_Nachname")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_Telefon")));
                    list.add(cursor.getString(cursor.getColumnIndex("F_email")));
                    list.add(cursor.getString(cursor.getColumnIndex("Flug_Nr")));
                    list.add("\n");
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }
        return list;
    }*/
}
