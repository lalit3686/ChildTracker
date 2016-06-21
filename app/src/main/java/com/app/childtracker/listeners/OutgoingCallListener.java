package com.app.childtracker.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.databases.TableOutgoingCall;

/**
 * Created by plalit on 3/10/2016.
 */
public class OutgoingCallListener extends BroadcastReceiver {

    private static final String TAG = OutgoingCallListener.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            AppLog.e(TAG, "Outgoing - "+outgoingNumber);
            MyApplication.showToast(context, "Outgoing - "+outgoingNumber);
            long rowId = TableOutgoingCall.insertOutgoingCall(outgoingNumber, System.currentTimeMillis());
            AppLog.e(TAG, String.valueOf(rowId));
        }
    }
}
