package com.dan.toyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dan.toyapp.entity.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danmalone on 18/10/2013.
 */


public class CountriesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SqlLiteHelperCountries dbHelper;
    private String[] allColumns = {
            SqlLiteHelperCountries.COLUMN_CODE, SqlLiteHelperCountries.COLUMN_NAME};

    public CountriesDataSource(Context context) {
        dbHelper = new SqlLiteHelperCountries(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public Country getCountry(String code) throws SQLException {
        Cursor cursor = database.query(SqlLiteHelperCountries.TABLE_COUNTRIES,
                allColumns, SqlLiteHelperCountries.COLUMN_CODE + " = '" + code + "'", null,
                null, null, null);
        cursor.moveToFirst();
        Country newCountry = cursorToCountry(cursor);
        cursor.close();
        return newCountry;
    }

    public boolean addCountry(String code, String name) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(SqlLiteHelperCountries.COLUMN_CODE, code);
        values.put(SqlLiteHelperCountries.COLUMN_NAME, name);
        long insertId = database.insert(SqlLiteHelperCountries.TABLE_COUNTRIES, null,
                values);
        if (insertId == -1)
            return false;
        else
            return true;
    }

    public void deleteCountry(String code) throws SQLException {
        System.out.println("Comment deleted with code: " + code);
        database.delete(SqlLiteHelperCountries.TABLE_COUNTRIES, SqlLiteHelperCountries.COLUMN_CODE
                + " = " + "'" + code + "'", null);
    }

    public List<Country> getAllCountries() throws SQLException {
        List<Country> countries = new ArrayList<Country>();

        Cursor cursor = database.query(SqlLiteHelperCountries.TABLE_COUNTRIES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Country country = cursorToCountry(cursor);
            countries.add(country);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return countries;
    }

    private Country cursorToCountry(Cursor cursor) throws SQLException {
        Country country = new Country();
        country.setCountryCode(cursor.getString(0));
        country.setName(cursor.getString(1));
        return country;
    }

}
