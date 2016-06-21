package com.app.childtracker.databases;

import android.content.ContentValues;
import android.database.Cursor;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.models.IncomingCallModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plalit on 5/31/2016.
 */
public class TableIncomingCall {

    private static final String TAG = TableIncomingCall.class.getSimpleName();

    private static final String TABLE_NAME = "incoming_calls";
    private static final String COL_NUMBER = "number";
    private static final String COL_DATE = "date";

    public static long insertIncomingCall(String number, long millis){

        ContentValues values = new ContentValues();
        values.put(COL_NUMBER, number);
        values.put(COL_DATE, millis);

        return MyApplication.getApplicationDatabase().insert(TABLE_NAME, null, values);
    }

    public static List<IncomingCallModel> getAllIncomingCalls(){
        List<IncomingCallModel> mIncomingCallModels = new ArrayList<>();

        Cursor cursor = MyApplication.getApplicationDatabase().rawQuery("select * from "+TABLE_NAME, null);

        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_NUMBER))));
                AppLog.e(TAG, String.valueOf(cursor.getDouble(cursor.getColumnIndex(COL_DATE))));

                mIncomingCallModels.add(new IncomingCallModel(cursor.getString(cursor.getColumnIndex(COL_NUMBER)),
                        cursor.getLong(cursor.getColumnIndex(COL_DATE))));
            }
        }

        cursor.close();

        return mIncomingCallModels;
    }
}
