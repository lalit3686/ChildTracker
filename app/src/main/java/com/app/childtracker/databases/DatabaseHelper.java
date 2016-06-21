package com.app.childtracker.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String TAG = DatabaseHelper.class.getSimpleName();
	public static String DB_NAME = "childtracker.db";
	private static int DB_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	private static void isDirectoryPresent(String db_path) {
		/**
		 * create "databases" directory if not in existence in data/data/package_name/databases/
		 */
		File file = new File(db_path.substring(0, db_path.indexOf("/"+DB_NAME)));
		
		/**
		 * check if databases folder exists or not.
		 */
		if(!file.isDirectory())
			file.mkdir();
	}
	
	public static void createDatabaseFile(){
		
		/**
		 *  data/data/package_name/databases/db_name.db
		 */
		String dbPath = MyApplication.getApplicationInstance().getDatabasePath(DB_NAME).toString();
		isDirectoryPresent(dbPath);
		
		File file = new File(dbPath);
		AppLog.e(TAG, file.getAbsolutePath());
		if(file.exists()){
			AppLog.e(TAG, "File already exists");
		}
		else{
			copyDatabase(file);
		}
	}
	
	private static void copyDatabase(File file) {
		try {
			/**
			 * create new file if it is not in existence
			 */
			file.createNewFile();   
			InputStream is = MyApplication.getApplicationInstance().getAssets().open(DB_NAME);
			OutputStream write = new FileOutputStream(file);
			
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				write.write(buffer, 0, length);
			}
			is.close();
			write.close();
			AppLog.e(TAG, "File does not exists & Newly created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
