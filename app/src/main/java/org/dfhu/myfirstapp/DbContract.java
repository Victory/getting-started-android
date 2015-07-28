package org.dfhu.myfirstapp;

import android.provider.BaseColumns;

public final class DbContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATETIME";
    private static final String COMMA_SEP = " , ";

    public DbContract() {} // you shouldn't create an instance

    public abstract static class LifeCycleEventsHistory implements BaseColumns {
        public static final String TABLE_NAME = "life_cycle_events";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_DATE = "date_added";
    }

    public static String getLifeCycleEventsHistoryCreateString () {
        return "CREATE TABLE " + LifeCycleEventsHistory.TABLE_NAME + " (" +
                LifeCycleEventsHistory._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                LifeCycleEventsHistory.COLUMN_NAME_KEY + TEXT_TYPE + COMMA_SEP +
                LifeCycleEventsHistory.COLUMN_NAME_VALUE + TEXT_TYPE + COMMA_SEP +
                LifeCycleEventsHistory.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP +
                ");";
    }

    public static String getLifeCycleEventsHistoryDropString () {
        return "DROP TABLE IF EXISTS " + LifeCycleEventsHistory.TABLE_NAME;
    }

}
