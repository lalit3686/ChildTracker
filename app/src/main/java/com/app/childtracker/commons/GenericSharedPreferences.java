package com.app.childtracker.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.app.childtracker.application.MyApplication;

public class GenericSharedPreferences {

	private SharedPreferences sharedPrefs;
	private Editor editor;
	private int MODE = -1;
	private static final int READ = 0, WRITE = 1;

	public GenericSharedPreferences(String prefName, int MODE){
		sharedPrefs = MyApplication.getApplicationInstance().getSharedPreferences(prefName, Context.MODE_PRIVATE);

		switch (MODE){
			case READ:
				break;
			case WRITE:
				editor = sharedPrefs.edit();
				break;
		}
	}

	public GenericSharedPreferences putInt(String key, int value) {
		if(editor != null){
			editor.putInt(key, value);
			return this;
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}
	}

	public int getInt(String key, int defValue) {
		return sharedPrefs.getInt(key, defValue);
	}

	public GenericSharedPreferences putString(String key, String value) {
		if(editor != null){
			editor.putString(key, value);
			return this;
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}
	}

	public String getString(String key, String defValue) {
		return sharedPrefs.getString(key, defValue);
	}

	public GenericSharedPreferences putBoolean(String key, boolean value) {
		if(editor != null) {
			editor.putBoolean(key, value);
			return this;
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}

	}

	public boolean getBoolean(String key, boolean defValue) {
		return sharedPrefs.getBoolean(key, defValue);
	}

	public GenericSharedPreferences putLong(String key, long value) {
		if(editor != null) {
			editor.putLong(key, value);
			return this;
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}
	}

	public long getLong(String key, long defValue) {
		return sharedPrefs.getLong(key, defValue);
	}

	public GenericSharedPreferences commit(){
		if(editor != null) {
			editor.commit();
			return this;
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}
	}

	public void remove(String key) {
		if(editor != null) {
			editor.remove(key);
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}
	}

	public GenericSharedPreferences clearAllPreferences(String prefName) {
		if(editor != null) {
			editor.clear().commit();
			return this;
		}
		else{
			throw new RuntimeException("Editor cannot be null, please check if you have chosen WRITE MODE");
		}
	}
}
