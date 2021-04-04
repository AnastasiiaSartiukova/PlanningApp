package com.example.planning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PlanningDB";

    private final String TABLE_NAME = "users";

    private DatabaseHelper db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "LogIn", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table if not exists " + TABLE_NAME + "(userID integer primary key, user_email text, user_password text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);

        onCreate(db);
    }
}
