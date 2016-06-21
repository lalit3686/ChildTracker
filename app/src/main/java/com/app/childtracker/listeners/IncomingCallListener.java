package com.app.childtracker.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.app.childtracker.R;
import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.commons.GenericSharedPreferences;
import com.app.childtracker.databases.TableIncomingCall;

/**
 * Created by plalit on 3/10/2016.
 */
public class IncomingCallListener extends BroadcastReceiver {

    private static final String TAG = IncomingCallListener.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        /**
         * Registering only once so that listener doesn't fire multiple times
         */
        if(!isTelephonyManagerRegistered(context)){
            setTelephonyManagerRegistered(context);
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(new MyPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
        }

        /**
         * to stop listening
         * telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
         */
    }

    private boolean isTelephonyManagerRegistered(Context context){
        return new GenericSharedPreferences(context.getString(R.string.pref_settings), 0)
                .getBoolean(context.getString(R.string.pref_boolean_telephone_manager), false);
    }

    private void setTelephonyManagerRegistered(Context context){
        new GenericSharedPreferences(context.getString(R.string.pref_settings), 1)
                .putBoolean(context.getString(R.string.pref_boolean_telephone_manager), true)
                .commit();
    }

    private class MyPhoneStateListener extends PhoneStateListener {

        private Context context;

        MyPhoneStateListener(Context context){
            this.context = context;
        }
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:
                    AppLog.e(TAG, "Incoming - "+incomingNumber);
                    MyApplication.showToast(context, "Incoming - "+incomingNumber);
                    long rowId = TableIncomingCall.insertIncomingCall(incomingNumber, System.currentTimeMillis());
                    AppLog.e(TAG, String.valueOf(rowId));
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }
}
