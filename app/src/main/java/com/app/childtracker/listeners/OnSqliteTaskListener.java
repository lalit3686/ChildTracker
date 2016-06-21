package com.app.childtracker.listeners;

public interface OnSqliteTaskListener {
	void onSqliteTaskPreExecute();
	void onSqliteTaskProgressUpdate(int queryType, Object values);
	void onSqliteTaskPostExecute(int queryType, Object result);
	void onSqliteTaskCancelled(int queryType, Object result);
}
