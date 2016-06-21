package com.app.childtracker.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.databases.TableIncomingSms;

/**
 * Created by Lalit T. Poptani on 12/03/2016.
 */
public class IncomingSMSListener extends BroadcastReceiver {

    private static final String TAG = IncomingSMSListener.class.getSimpleName();
    private SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        readIncomingSMS(context, intent);
    }

    private void readIncomingSMS(Context context, Intent intent){
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    AppLog.e("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    MyApplication.showToast(context, "senderNum: "+ senderNum + ", message: " + message);
                    long rowId = TableIncomingSms.insertIncomingSms(senderNum, message, System.currentTimeMillis());
                    AppLog.e(TAG, String.valueOf(rowId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
