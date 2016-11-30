package com.handong.termproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExampleActivity extends AppCompatActivity {
    static final String PACKAGE_NAME = "com.handong.termproject";
    static final String DB_NAME = "map.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Button b = (Button)findViewById(R.id.button_mapinfo);
        b.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(ExampleActivity.this, MapsActivity.class);
                startActivity(intent);


                /*
                ContentResolver resolver = getContentResolver();
                String[] projection = new String[]{MapInfoProvider.KEY_ID, MapInfoProvider.KEY_LATITUDE,
                        MapInfoProvider.KEY_LONGITUDE};
                Cursor cursor =
                        resolver.query(MapInfoProvider.CONTENT_URI,
                                projection,
                                null,
                                null,
                                null);
                if (cursor.moveToFirst()) {
                    do {
                        long id = cursor.getLong(0);
                        String word = cursor.getString(1);
                        // do something meaningful
                        Log.d("tag","cursor");
                    } while (cursor.moveToNext());
                }
                */

                /*
                String[] projection = new String[]{MapInfoProvider.KEY_ID, MapInfoProvider.KEY_LATITUDE,
                        MapInfoProvider.KEY_LONGITUDE};
                MapInfoProvider cp = new MapInfoProvider();
                Cursor cursor = cp.query(MapInfoProvider.CONTENT_URI,projection,null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        long id = cursor.getLong(0);
                        String word = cursor.getString(1);
                        // do something meaningful
                        Log.d("tag","cursor");
                    } while (cursor.moveToNext());
                }
                */
            }
        });
/*
        MapInfoDBOpenHelper mHelper = new MapInfoDBOpenHelper(ExampleActivity.this,"map.db",null,1);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("SELECT _id FROM toilet",null);
        if(cursor.moveToFirst()) {
            Log.d("ddd",""+cursor.getDouble(0));
        }

        ContentResolver cr = getConteinpu
        String[] result_columns = new String[] {
                MapInfoProvider.KEY_ID,
                MapInfoProvider.KEY_LATITUDE,
                MapInfoProvider.KEY_LONGITUDE
        };

        Uri rowAddress = ContentUris.withAppendedId(MapInfoProvider.CONTENT_URI, 1);

        // Replace these with valid SQL statements as necessary.
        String where = null;
        String whereArgs[] = null;
        String order = null;

        // Return the specified rows.
        Cursor resultCursor = cr.query(rowAddress, result_columns, where, whereArgs, order);
        */
    }
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
    public boolean isCheckDB(Context mContext){
        String filePath = "/data/data/" + PACKAGE_NAME + "/databases/" + DB_NAME;
        File file = new File(filePath);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void copyDB(Context mContext){
        Log.d("MiniApp", "copyDB");
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/" + PACKAGE_NAME + "/databases";
        String filePath = "/data/data/" + PACKAGE_NAME +"/databases/"+ DB_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = manager.open("database/"+ DB_NAME);
            BufferedInputStream bis = new BufferedInputStream(is);

            if (folder.exists()) {
            }else{
                folder.mkdirs();
            }


            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }

            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();

        } catch (IOException e) {
            Log.e("ErrorMessage : ", e.getMessage());
        }
    }


}
