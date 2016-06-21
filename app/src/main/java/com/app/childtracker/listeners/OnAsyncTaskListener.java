package com.app.childtracker.listeners;

public interface OnAsyncTaskListener {
	void onAsyncTaskPreExecute(int taskType);
	void onAsyncTaskProgressUpdate(int taskType, Object values);
	void onAsyncTaskPostExecute(int taskType, Object result);
	void onAsyncTaskFailed(int errorCode, String errorMessage);
	void onAsyncTaskCancelled(int taskType, Object result);
}
