package com.app.childtracker.application;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.app.childtracker.databases.DatabaseHelper;
import com.app.childtracker.listeners.OutgoingSMSListener;

import static com.app.childtracker.commons.ConstantCodes.CONTENT_SMS_URI;

public class MyApplication extends Application{

	private static MyApplication instance;
	private static SQLiteDatabase database;

	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;

		DatabaseHelper.createDatabaseFile();
		registerOutgoingSMSListener();
	}

	private void registerOutgoingSMSListener(){
		ContentObserver observer = new OutgoingSMSListener(this);
		getContentResolver().registerContentObserver(Uri.parse(CONTENT_SMS_URI), true, observer);
	}

	public static MyApplication getApplicationInstance() {
		return instance;
	}
	
	public static SQLiteDatabase getApplicationDatabase() {
		if(database == null || !database.isOpen()){
			database = new DatabaseHelper(getApplicationInstance()).getWritableDatabase();
		}
		return database;
	}
	
	public static void closeDatabase() {
		if (database != null && database.isOpen()) {
			database.close();
		}
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static boolean isDeviceConnectedToInternet() {
		ConnectivityManager mConnectivityMgrdevice = (ConnectivityManager) getApplicationInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkinfoDevice = mConnectivityMgrdevice.getActiveNetworkInfo();
		
		if (mNetworkinfoDevice != null && mNetworkinfoDevice.isConnected()){
			return true;
		}
		return false;
	}
}
