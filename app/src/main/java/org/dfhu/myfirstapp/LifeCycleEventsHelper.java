package org.dfhu.myfirstapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LifeCycleEventsHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "life_cycle_events";
    public static final String COLUMN_NAME_KEY = "key";
    public static final String COLUMN_NAME_VALUE = "value";
    public static final String COLUMN_NAME_DATE_ADDED = "date_added";
    public static final String[] allColumns = {
            LifeCycleEventsHelper.COLUMN_NAME_KEY,
            LifeCycleEventsHelper.COLUMN_NAME_VALUE,
            LifeCycleEventsHelper.COLUMN_NAME_DATE_ADDED
    };

    // these should be in a super class
    public static final String _ID = "_ID";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATETIME";
    private static final String COMMA_SEP = " , ";

    public static final int DB_VERSION = 1;

    private static final String CREATE_TABLE_STRING =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    COLUMN_NAME_KEY + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VALUE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_DATE_ADDED + DATE_TYPE + COMMA_SEP +  ");";

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
        db.execSQL("DROP TABL IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_TABLE_STRING);
    }
}
