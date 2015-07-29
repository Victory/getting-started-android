package org.dfhu.myfirstapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class LifeCycleEventsSource {

    private LifeCycleEventsHelper dbHelper;
    private SQLiteDatabase db;



    public LifeCycleEventsSource(Context context) {
        dbHelper = new LifeCycleEventsHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public LifeCycleEvent insertValue (String key, String value) {
        ContentValues values = new ContentValues();
        values.put(LifeCycleEventsHelper.COLUMN_NAME_KEY, key);
        values.put(LifeCycleEventsHelper.COLUMN_NAME_VALUE, value);

        long insertId = db.insert(LifeCycleEventsHelper.TABLE_NAME, null, values);
        Cursor cursor = db.query(LifeCycleEventsHelper.TABLE_NAME,
                LifeCycleEventsHelper.allColumns,
                LifeCycleEventsHelper._ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();

        return cursorToLifeCycleEvent(cursor);
    }

    public LifeCycleEvent cursorToLifeCycleEvent (Cursor cursor) {
        LifeCycleEvent lifeCycleEvent = new LifeCycleEvent();
        lifeCycleEvent.setKey(cursor.getString(0));
        lifeCycleEvent.setValue(cursor.getString(1));

        return lifeCycleEvent;
    }

}
