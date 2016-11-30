package com.handong.termproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class MapInfoProvider extends ContentProvider {

    // Create the constatns used to differentiate between the different URI requests.
    public static final String AUTHORITY = "com.handong.termproject.mapinfoprovider";
    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final String myURI = "content://" + AUTHORITY + "/toilet";

    private static final UriMatcher uriMatcher;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    // Populate the UriMatcher object, where a URI ending in 'toilet' will
    // correspond to a request for all items, and 'toilet/[rowID]'
    // represents a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.handong.termproject.mapinfoprovider","toilet",ALLROWS);
        uriMatcher.addURI("com.handong.termproject.mapinfoprovider","toilet/#",SINGLE_ROW);
    }

    public static final Uri CONTENT_URI = Uri.parse(myURI);

//    public MapInfoDBOpenHelper mapInfoDBOpenHelper;

//    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public SQLiteDatabase db;

    public MapInfoProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri _uri) {
        switch (uriMatcher.match(_uri)) {
            case ALLROWS:
                return "vnd.android.cursor.dir/com.handong.termproject";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/com.handong.termproject";
            default:
                throw new IllegalArgumentException("Unsupported URI: "+ _uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        Log.d("MapInfoProvider","onCreate()");
        DataBaseHelper myDbHelper = new DataBaseHelper(getContext());
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Uanable to create database");
        }

        try {
            myDbHelper.openDataBase();
        } catch(SQLException sqle) {
            throw sqle;
        }

        db = myDbHelper.getSQLiteDatabase();

        /*
        mapInfoDBOpenHelper = new MapInfoDBOpenHelper(getContext()
        ,MapInfoDBOpenHelper.DATABASE_NAME, null, 1);
        Log.d("MapInfoProvider","onCreate");
        */


        if (db == null) {
            Log.d("MapInfoProvider","db is null");
            return false;
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // If this is a row query, limit the result to the passed in row.

        switch(uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowNumber =uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID+ "=" + rowNumber);
            default: break;
        }

        queryBuilder.setTables("toilet");

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);

        return cursor;
        /*return db.query(MapsActivity.DB_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);*/
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
/*
    private class MapInfoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "map.db";
        private static final String DATABASE_TABLE = "toilet";

        public MapInfoDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
*/

}



















