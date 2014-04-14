package com.dan.toyapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by danmalone on 18/10/2013.
 */
public class SqlLiteHelperCountries extends SQLiteOpenHelper {

    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_NAME = "countries.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COUNTRIES + "(" + COLUMN_CODE
            + " text not null primary key,"+ COLUMN_NAME + " text not null)";

    public SqlLiteHelperCountries(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlLiteHelperCountries.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        onCreate(db);
    }

}
