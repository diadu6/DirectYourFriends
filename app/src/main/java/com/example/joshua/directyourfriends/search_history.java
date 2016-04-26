package com.example.joshua.directyourfriends;

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

public class search_history {
    //search_history constants
    public static final String DIRECTIONS_NAME = "search_history.db";
    public static final int DIRECTIONS_VERSION = 1;

    //directions table constants
    public static final String DIRECTIONS_TABLE = "directions";

    public static final String DIRECTIONS_ID = "_id";
    public static final int DIRECTIONS_ID_COL = 0;

    public static final String DIRECTIONS_SEARCH = "Search_Text";
    public static final int DIRECTIONS_SEARCH_COL = 1;

    //create and drop methods
    public static final String CREATE_DIRECTIONS_TABLE =
            "CREATE TABLE " + DIRECTIONS_TABLE + " (" + DIRECTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DIRECTIONS_SEARCH + "TEXT NOT NULL);";

    public static final String DROP_DIRECTIONS_TABLE =
            "DROP TABLE IF EXISTS " + DIRECTIONS_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase search_history) {
            search_history.execSQL(CREATE_DIRECTIONS_TABLE);
        }

        //arbitrary upgrade method
        @Override
        public void onUpgrade(SQLiteDatabase search_history, int oldVersion, int newVersion) {
            Log.d("Task list", "Upgrading db from version " + oldVersion + "to " + newVersion);
            search_history.execSQL(com.example.joshua.directyourfriends.search_history.DROP_DIRECTIONS_TABLE);
            onCreate(search_history);
        }
    }

    //defining open readable and writable methods
    private SQLiteDatabase search_history;
    private DBHelper dbHelper;

    public search_history(Context context) {
        dbHelper = new DBHelper(context, DIRECTIONS_NAME, null, DIRECTIONS_VERSION);
    }

    private void openReadableDB() {
        search_history = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        search_history = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (search_history != null)
            search_history.close();
    }

    //grab all rows
    public ArrayList<getDirectionsDB> getSearches(String directionSearch) {
        String where = DIRECTIONS_ID + "- ?";
        int id = getDirections(directionSearch).getId();
        String[] whereArgs = { Integer.toString(id) };

        this.openReadableDB();
        Cursor cursor = search_history.query(DIRECTIONS_TABLE, null, where, whereArgs, null, null, null);
        ArrayList<getDirectionsDB> searches = new ArrayList<getDirectionsDB>();
        while (cursor.moveToNext()) {
            searches.add(getSearchFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return searches;
    }

    public getDirectionsDB getDirections(String name) {
        String where = DIRECTIONS_NAME + "= ?";
        String[] whereArgs = { name };

        openReadableDB();
        Cursor cursor = search_history.query(DIRECTIONS_TABLE, null,
                where, whereArgs, null, null, null);
        getDirectionsDB directions = null;
        cursor.moveToFirst();
        directions = new getDirectionsDB();
                //cursor.getInt(DIRECTIONS_ID_COL),
                //cursor.getString(DIRECTIONS_SEARCH_COL));
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return directions;
    }

//          grab one row at a time

//    public getDirectionsDB getSearch(int id) {
//        String where = DIRECTIONS_ID + "= ?";
//       String[] whereArgs = { Integer.toString(id) };
//
//        this.openReadableDB();
//        Cursor cursor = search_history.query(DIRECTIONS_TABLE, null, where, whereArgs, null, null, null);
//        cursor.moveToFirst();
//        getDirectionsDB search = getSearchFromCursor(cursor);
//        if (cursor != null)
//            cursor.close();
//        this.closeDB();

//        return search;
//    }

    //method to retrieve data from cursor
    private static getDirectionsDB getSearchFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        else {
            try {
                getDirectionsDB search = new getDirectionsDB();
                        //cursor.getString(DIRECTIONS_ID_COL),
                        //cursor.getString(DIRECTIONS_SEARCH_COL));
                return search;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertDirections(getDirectionsDB search) {
        ContentValues cv = new ContentValues();
        cv.put(DIRECTIONS_ID, search.getId());
        cv.put(DIRECTIONS_SEARCH, search.getSearch());

        this.openWriteableDB();
        long rowID = search_history.insert(DIRECTIONS_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }
}