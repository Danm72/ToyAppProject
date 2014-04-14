package com.dan.toyapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by danmalone on 18/10/2013.
 */
public class SqlLiteHelperCities extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "cities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_REGION = "region";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "cities.db";



    private static int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table "
                    + TABLE_NAME
                    + "("
                    + COLUMN_ID
                    + " integer primary key,"
                    + COLUMN_COUNTRY
                    + " text not null,"
                    + COLUMN_REGION
                    + " text not null,"
                    + COLUMN_CITY
                    + " text not null,"
                    + COLUMN_LATITUDE
                    + " REAL NOT NULL,"
                    + COLUMN_LONGITUDE
                    + " REAL NOT NULL,"
                    + COLUMN_COMMENT
                    + " text not null"
                    + ")";

    public SqlLiteHelperCities(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public void setDatabaseVersion(int version) {
        DATABASE_VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlLiteHelperCities.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
