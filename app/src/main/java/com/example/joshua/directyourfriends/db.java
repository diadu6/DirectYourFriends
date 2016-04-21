package com.example.joshua.directyourfriends;

/**
 * Created by Joshua on 4/7/2016.
 */

/**
 * Created by user on 3/31/2016.
 */

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class db {
    //db constants
    public static final String DB_NAME = "db.db";
    public static final int DB_VERSION = 1;

    //list table constants
    public static final String LIST_TABLE = "list";

    public static final String LIST_ID = "_id";
    public static final int LIST_ID_COL = 0;

    public static final String LIST_NAME = "list_name";
    public static final int LIST_NAME_COL = 1;

    //directions table constants
    public static final String DIRECTIONS_TABLE = "directions";

    public static final String DIRECTIONS_ID = "_id";
    public static final int DIRECTIONS_ID_COL = 0;

    public static final String DIRECTIONS_LIST_ID = "list_id";
    public static final int DIRECTIONS_LIST_ID_COL = 1;

    public static final String DIRECTIONS_NAME = "directions_name";
    public static final int DIRECTIONS_NAME_COL = 2;

    public static final String DIRECTIONS_NOTES = "notes";
    public static final int DIRECTIONS_NOTES_COL = 3;

    public static final String DIRECTIONS_HIDDEN = "who_sent";
    public static final int DIRECTIONS_HIDDEN_COL = 4;

    //create + drop table
    public static final String CREATE_LIST_TABLE =
            "CREATE TABLE " + LIST_TABLE + " (" + LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIST_NAME + " TEXT NOT NULL UNIQUE);";

    public static final String CREATE_DIRECTIONS_TABLE = "CREATE TABLE " + DIRECTIONS_TABLE + " (" + DIRECTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DIRECTIONS_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DIRECTIONS_NAME + "TEXT NOT NULL, " + DIRECTIONS_NOTES + " TEXT, " + DIRECTIONS_HIDDEN + " TEXT);";

    public static final String DROP_LIST_TABLE =
            "DROP TABLE IF EXISTS " + LIST_TABLE;

    public static final String DROP_DIRECTIONS_TABLE =
            "DROP TABLE IF EXISTS " + DIRECTIONS_TABLE;
}
