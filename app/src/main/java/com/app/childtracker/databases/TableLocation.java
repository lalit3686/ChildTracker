package com.app.childtracker.databases;

import android.content.ContentValues;
import android.database.Cursor;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalit T. Poptani on 12/03/2016.
 */
public class TableLocation {

    private static final String TAG = TableLocation.class.getSimpleName();

    private static final String TABLE_NAME = "location";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";
    private static final String COL_LOCALITY = "locality";
    private static final String COL_COUNTRY = "country";
    private static final String COL_DATE = "date";

    public static long insertLocation(double latitude, double longitude, String locality, String country, long millis){

        ContentValues values = new ContentValues();
        values.put(COL_LATITUDE, latitude);
        values.put(COL_LONGITUDE, longitude);
        values.put(COL_LOCALITY, locality);
        values.put(COL_COUNTRY, country);
        values.put(COL_DATE, millis);

        return MyApplication.getApplicationDatabase().insert(TABLE_NAME, null, values);
    }

    public static List<LocationModel> getAllLocations(){
        List<LocationModel> mLocationModels = new ArrayList<>();

        Cursor cursor = MyApplication.getApplicationDatabase().rawQuery("select * from "+TABLE_NAME, null);

        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_LATITUDE))));
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_LONGITUDE))));
                AppLog.e(TAG, cursor.getString(cursor.getColumnIndex(COL_LOCALITY)));
                AppLog.e(TAG, cursor.getString(cursor.getColumnIndex(COL_COUNTRY)));

                mLocationModels.add(new LocationModel(cursor.getDouble(cursor.getColumnIndex(COL_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(COL_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(COL_LOCALITY)),
                        cursor.getString(cursor.getColumnIndex(COL_COUNTRY)),
                        cursor.getLong(cursor.getColumnIndex(COL_DATE))));
            }
        }

        cursor.close();

        return mLocationModels;
    }
}
