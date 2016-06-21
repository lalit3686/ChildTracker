package com.app.childtracker.databases;

import android.content.ContentValues;
import android.database.Cursor;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.models.IncomingSmsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plalit on 5/31/2016.
 */
public class TableIncomingSms {

    private static final String TAG = TableIncomingSms.class.getSimpleName();

    private static final String TABLE_NAME = "incoming_sms";
    private static final String COL_NUMBER = "number";
    private static final String COL_MESSAGE = "message";
    private static final String COL_DATE = "date";

    public static long insertIncomingSms(String number, String message, long millis){

        ContentValues values = new ContentValues();
        values.put(COL_NUMBER, number);
        values.put(COL_MESSAGE, message);
        values.put(COL_DATE, millis);

        return MyApplication.getApplicationDatabase().insert(TABLE_NAME, null, values);
    }

    public static List<IncomingSmsModel> getAllIncomingSms(){
        List<IncomingSmsModel> mIncomingSmsModels = new ArrayList<>();

        Cursor cursor = MyApplication.getApplicationDatabase().rawQuery("select * from "+TABLE_NAME, null);

        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_NUMBER))));
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_MESSAGE))));
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_DATE))));

                mIncomingSmsModels.add(new IncomingSmsModel(cursor.getString(cursor.getColumnIndex(COL_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(COL_MESSAGE)),
                        cursor.getLong(cursor.getColumnIndex(COL_DATE))));
            }
        }

        cursor.close();

        return mIncomingSmsModels;
    }
}
