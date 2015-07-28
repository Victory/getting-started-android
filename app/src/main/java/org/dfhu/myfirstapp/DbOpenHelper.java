package org.dfhu.myfirstapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "myFirstApp.db";

    // if you change schema update this version number
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.getLifeCycleEventsHistoryCreateString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // there is no upgrade policy yet
    }

    public void insert (String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put(DbContract.LifeCycleEventsHistory.COLUMN_NAME_KEY, key);
        v.put(DbContract.LifeCycleEventsHistory.COLUMN_NAME_VALUE, value);
        db.insert(DbContract.LifeCycleEventsHistory.TABLE_NAME, null, v);
    }
}
