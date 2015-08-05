package org.dfhu.myfirstapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * XXX: a lot of this should be done async
 */
public class LifeCycleEventsSource {

    private LifeCycleEventsHelper dbHelper;
    private SQLiteDatabase db;



    public LifeCycleEventsSource(Context context) {
        dbHelper = new LifeCycleEventsHelper(context);
    }

    public void open() throws SQLException {

        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
    }

    public void close() {
        dbHelper.close();
    }

    public LifeCycleEvent insertValue (String key, String value) throws SQLException {

        open();

        ContentValues values = new ContentValues();
        values.put(LifeCycleEventsHelper.COLUMN_NAME_KEY, key);
        values.put(LifeCycleEventsHelper.COLUMN_NAME_VALUE, value);
        values.put(LifeCycleEventsHelper.COLUMN_NAME_DATE_ADDED, LifeCycleEventsHelper.getNow());

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
        lifeCycleEvent.setKey(cursor.getString(LifeCycleEventsHelper.COLUMN_INDEX_KEY));
        lifeCycleEvent.setValue(cursor.getString(LifeCycleEventsHelper.COLUMN_INDEX_VALUE));
        lifeCycleEvent.setId(cursor.getInt(LifeCycleEventsHelper.COLUMN_INDEX_ID));
        lifeCycleEvent.setDateAdded(cursor.getString(LifeCycleEventsHelper.COLUMN_INDEX_DATE_ADDED));

        return lifeCycleEvent;
    }

    public void deleteById (Integer id) throws SQLException {
        open();
        db.delete(LifeCycleEventsHelper.TABLE_NAME,
                LifeCycleEventsHelper._ID + " = " + id.toString(),
                null);
    }

    public List<LifeCycleEvent> getAll () {
        ArrayList<LifeCycleEvent> all = new ArrayList<>();
        Cursor cursor = db.query(LifeCycleEventsHelper.TABLE_NAME,
                LifeCycleEventsHelper.allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            LifeCycleEvent evt = cursorToLifeCycleEvent(cursor);
            all.add(evt);
            cursor.moveToNext();
        }
        cursor.close();

        return all;
    }

    public void empty() throws SQLException {
        open();
        db.delete(LifeCycleEventsHelper.TABLE_NAME, null, null);
    }

}
