package com.app.childtracker.commons;

import android.os.AsyncTask;

import com.app.childtracker.listeners.OnAsyncTaskListener;

import java.util.ArrayList;
import java.util.List;

public class GenericAsyncTask extends AsyncTask<Object, Object, Object>{

	private OnAsyncTaskListener onAsyncTaskListener;
	private int taskType;
	private List<String> mQueryParams;
	private ArrayList<String> mPathParams;
	
	public GenericAsyncTask(OnAsyncTaskListener onAsyncTaskListener) {
		this.onAsyncTaskListener = onAsyncTaskListener;
	}

	public GenericAsyncTask addTaskType(int taskType) {
		this.taskType = taskType;
		return this;
	}
	
	public GenericAsyncTask addPathParams(ArrayList<String> mPathParams){
		this.mPathParams = mPathParams;
		return this;
	}
	
	public GenericAsyncTask addQueryParams(List<String> mQueryParams){
		this.mQueryParams = mQueryParams;
		return this;
	}
	
	@Override
	protected void onPreExecute() {
		onAsyncTaskListener.onAsyncTaskPreExecute(taskType);
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		switch (taskType) {

		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Object... values) {
		switch (taskType) {
		
		}
		onAsyncTaskListener.onAsyncTaskProgressUpdate(taskType, values);
	}

	@Override
	protected void onPostExecute(Object result) {
		switch (taskType) {
		}
		onAsyncTaskListener.onAsyncTaskPostExecute(taskType, result);
	}
	
	@Override
	protected void onCancelled(Object result) {
		onAsyncTaskListener.onAsyncTaskCancelled(taskType, result);
	}
}
