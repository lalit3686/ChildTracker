package com.app.childtracker.listeners;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.databases.TableOutgoingSms;

import java.util.Date;

import static com.app.childtracker.commons.ConstantCodes.CONTENT_SMS_URI;

/**
 * Created by Lalit T. Poptani on 12/03/2016.
 */
public class OutgoingSMSListener extends ContentObserver {

    private Context context;
    private static final String TAG = OutgoingSMSListener.class.getSimpleName();

    public OutgoingSMSListener(Context context) {
        super(new Handler());

        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        readOutgoingSMS();
    }

    private void readOutgoingSMS(){
        Cursor cursor = context.getContentResolver().query(Uri.parse(CONTENT_SMS_URI), null, null, null, null);
        AppLog.e("OutgoingSMSListener", "called");
        if (cursor.moveToNext()) {
            int messageType = cursor.getInt(cursor.getColumnIndex("type"));
            switch (messageType){
                case Telephony.TextBasedSmsColumns.MESSAGE_TYPE_SENT:
                    int dateColumn = cursor.getColumnIndex("date");
                    int bodyColumn = cursor.getColumnIndex("body");
                    int addressColumn = cursor.getColumnIndex("address");
                    String from = "0";
                    String to = cursor.getString(addressColumn);
                    Date now = new Date(cursor.getLong(dateColumn));
                    String message = cursor.getString(bodyColumn);
                    AppLog.e("OutgoingSMSListener", "to: "+ to + "; message: " + message);
                    MyApplication.showToast(context, dateColumn+" "+bodyColumn+" "+addressColumn+" "+" "+from+" "+to+" "+now+" "+message);
                    long rowId = TableOutgoingSms.insertOutgoingSms(to, message, System.currentTimeMillis());
                    AppLog.e(TAG, String.valueOf(rowId));
                    break;
            }
        }
    }
}
