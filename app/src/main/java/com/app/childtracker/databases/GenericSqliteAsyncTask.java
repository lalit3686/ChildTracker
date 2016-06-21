package com.app.childtracker.databases;

import android.app.Activity;
import android.os.AsyncTask;

import com.app.childtracker.listeners.OnSqliteTaskListener;
import com.app.childtracker.models.LocationModel;

import java.util.List;
import static com.app.childtracker.commons.ConstantCodes.ADD_LOCATION;
import static com.app.childtracker.commons.ConstantCodes.GET_ALL_LOCATION;

public class GenericSqliteAsyncTask extends AsyncTask<Integer, Void, Object>{

	private OnSqliteTaskListener onSqliteTaskListener;
	private String[] whereArgs;
	private int queryType;
	private Object contentValues;
	
	public GenericSqliteAsyncTask(Activity mActivity) {
		this.onSqliteTaskListener = (OnSqliteTaskListener) mActivity;
	}

	public GenericSqliteAsyncTask addQueryType(int queryType) {
		this.queryType = queryType;
		return this;
	}
	
	public GenericSqliteAsyncTask addQueryParameters(String[] whereArgs) {
		this.whereArgs = whereArgs;
		return this;
	}

	public GenericSqliteAsyncTask addContentValues(Object contentValues){
		this.contentValues = contentValues;
		return this;
	}
	
	@Override
	protected Object doInBackground(Integer... params) {
		
		switch (queryType) {
			case ADD_LOCATION:
				if(contentValues != null){
					List<LocationModel> locationModelList = (List<LocationModel>) contentValues;
					for(LocationModel model : locationModelList){
						TableLocation.insertLocation(model.getLatitude(), model.getLongitude(), model.getLocality(),
								model.getCountry(), model.getMillis());
					}
				}
				break;
			case GET_ALL_LOCATION:
				return TableLocation.getAllLocations();
		}
		return null;
	}
	@Override
	protected void onPostExecute(Object result) {
		onSqliteTaskListener.onSqliteTaskPostExecute(queryType, result);
	}
	
	@Override
	protected void onCancelled(Object result) {
		onSqliteTaskListener.onSqliteTaskCancelled(queryType, result);
	}
}
