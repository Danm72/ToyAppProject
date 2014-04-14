package com.dan.toyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dan.toyapp.entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danmalone on 18/10/2013.
 */


public class CitiesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqlLiteHelperCities dbHelper;
    private String[] allColumns = {
            SqlLiteHelperCities.COLUMN_ID,
            SqlLiteHelperCities.COLUMN_COUNTRY,
            SqlLiteHelperCities.COLUMN_REGION,
            SqlLiteHelperCities.COLUMN_CITY,
            SqlLiteHelperCities.COLUMN_LATITUDE,
            SqlLiteHelperCities.COLUMN_LONGITUDE,
            SqlLiteHelperCities.COLUMN_COMMENT
    };

    public CitiesDataSource(Context context) {
        dbHelper = new SqlLiteHelperCities(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public City getCity(int id) throws SQLException {
        Cursor cursor = database.query(SqlLiteHelperCities.TABLE_NAME,
                allColumns, SqlLiteHelperCities.COLUMN_ID + " = '" + id + "'", null,
                null, null, null);
        cursor.moveToFirst();
        City newCity = cursorToCountry(cursor);
        cursor.close();
        return newCity;
    }

    public boolean addCity(String id, String country, String region, String city, float latitude, float longitude, String comment) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(SqlLiteHelperCities.COLUMN_ID, id);
        values.put(SqlLiteHelperCities.COLUMN_COUNTRY, country);
        values.put(SqlLiteHelperCities.COLUMN_REGION, region);
        values.put(SqlLiteHelperCities.COLUMN_CITY, city);
        values.put(SqlLiteHelperCities.COLUMN_LATITUDE, latitude);
        values.put(SqlLiteHelperCities.COLUMN_LONGITUDE, longitude);
        values.put(SqlLiteHelperCities.COLUMN_COMMENT, comment);

        long insertId = database.insert(SqlLiteHelperCities.TABLE_NAME, null,
                values);
        if (insertId == -1)
            return false;
        else
            return true;
    }

    public boolean addCity(City city) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(SqlLiteHelperCities.COLUMN_ID, city.getId());
        values.put(SqlLiteHelperCities.COLUMN_COUNTRY, city.getCountry());
        values.put(SqlLiteHelperCities.COLUMN_REGION, city.getRegion());
        values.put(SqlLiteHelperCities.COLUMN_CITY, city.getCity());
        values.put(SqlLiteHelperCities.COLUMN_LATITUDE, city.getLatitude());
        values.put(SqlLiteHelperCities.COLUMN_LONGITUDE, city.getLongitude());
        values.put(SqlLiteHelperCities.COLUMN_COMMENT, city.getComment());

        long insertId = database.insert(SqlLiteHelperCities.TABLE_NAME, null,
                values);
        if (insertId == -1)
            return false;
        else
            return true;
    }

    public void deleteCountry(int id) throws SQLException {
        System.out.println("Comment deleted with code: " + id);
        database.delete(SqlLiteHelperCities.TABLE_NAME, SqlLiteHelperCities.COLUMN_ID
                + " = " + "'" + id + "'", null);
    }

    public List<City> getAllCities() throws SQLException {
        List<City> cities = new ArrayList<City>();

        Cursor cursor = database.query(SqlLiteHelperCities.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            City city = cursorToCountry(cursor);
            cities.add(city);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return cities;
    }

    private City cursorToCountry(Cursor cursor) throws SQLException {
        City city = new City();
        city.setId(cursor.getString(0));
        city.setCountry(cursor.getString(1));
        city.setRegion(cursor.getString(2));
        city.setCity(cursor.getString(3));
        city.setLatitude(Float.parseFloat(cursor.getString(4)));
        city.setLongitude(Float.parseFloat(cursor.getString(5)));
        city.setComment(cursor.getString(6));

        return city;
    }

    public void upgradeDB(){
        dbHelper.onUpgrade(database,dbHelper.getDatabaseVersion(), dbHelper.getDatabaseVersion()+1);
    }

}
