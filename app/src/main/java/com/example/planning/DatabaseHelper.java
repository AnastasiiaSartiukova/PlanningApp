package com.example.planning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "PlanningDB";

    private final String TABLE_NAME = "users";
    private final String TABLE2_NAME = "projects";
    private final String TABLE3_NAME = "membersInProject";

    private DatabaseHelper db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "ProjectApp", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String logIn = "Create table if not exists " + TABLE_NAME + "(userID integer primary key, user_email text, user_password text)";
        String projects = "Create table if not exists " + TABLE2_NAME + "(pID integer primary key, pName text, pDate text, pCreator text, pCost integer)";
        String memberInProject = "Create table if not exists " + TABLE3_NAME + "(id integer primary key, member text, project_id integer)";

        db.execSQL(logIn);
        db.execSQL(memberInProject);
        db.execSQL(projects);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        db.execSQL("drop table " + TABLE2_NAME);
        db.execSQL("drop table " + TABLE3_NAME);

        onCreate(db);
    }
}
