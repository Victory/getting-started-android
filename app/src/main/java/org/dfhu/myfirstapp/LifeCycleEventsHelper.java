package org.dfhu.myfirstapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LifeCycleEventsHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "life_cycle_events";
    public static final String COLUMN_NAME_KEY = "key";
    public static final String COLUMN_NAME_VALUE = "value";
    public static final String COLUMN_NAME_DATE_ADDED = "date_added";
    public static final String _ID = "_ID";

    public static final int COLUMN_INDEX_KEY = 0;
    public static final int COLUMN_INDEX_VALUE = 1;
    public static final int COLUMN_INDEX_DATE_ADDED = 2;
    public static final int COLUMN_INDEX_ID = 3;

    public static final String[] allColumns = {
            LifeCycleEventsHelper.COLUMN_NAME_KEY,
            LifeCycleEventsHelper.COLUMN_NAME_VALUE,
            LifeCycleEventsHelper.COLUMN_NAME_DATE_ADDED,
            LifeCycleEventsHelper._ID
    };

    // these should be in a super class

    private static final String TEXT_TYPE = " TEXT ";
    private static final String DATE_TYPE = " DATETIME ";
    public static final String DEFAULT_TO_NOW = " DEFAULT CURRENT_TIMESTAMP ";
    private static final String COMMA_SEP = " , ";

    public static final int DB_VERSION = 1;

    private static final String CREATE_TABLE_STRING =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    COLUMN_NAME_KEY + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VALUE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_DATE_ADDED + DATE_TYPE + DEFAULT_TO_NOW + ");";

    public LifeCycleEventsHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LifeCycleEventsHelper.class.getName(),
                "upgrading database to version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_TABLE_STRING);
    }

    public static String getNow(){
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
