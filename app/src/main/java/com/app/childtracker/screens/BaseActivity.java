package com.app.childtracker.screens;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import java.text.SimpleDateFormat;

public abstract class BaseActivity extends FragmentActivity{

	protected SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	abstract void initComponents();
	abstract void addListeners();
	
	private ProgressDialog mProgressDialog = null;

	protected void showProgressDialog() {
		if(mProgressDialog == null || !mProgressDialog.isShowing()){
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.show();
		}
	}

	protected void closeProgressDialog() {
		if(mProgressDialog != null && mProgressDialog.isShowing()){
			mProgressDialog.dismiss();
		}
	}
}
