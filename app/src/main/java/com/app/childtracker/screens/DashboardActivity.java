package com.app.childtracker.screens;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.app.childtracker.R;
import com.app.childtracker.application.MyApplication;
import com.app.childtracker.commons.AppLog;
import com.app.childtracker.commons.ConstantCodes;
import com.app.childtracker.databases.GenericSqliteAsyncTask;
import com.app.childtracker.listeners.OnSqliteTaskListener;
import com.app.childtracker.models.LocationModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.app.childtracker.commons.ConstantCodes.ADD_LOCATION;
import static com.app.childtracker.commons.ConstantCodes.GET_ALL_LOCATION;

public class DashboardActivity extends BaseActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, OnSqliteTaskListener {

    private static final String TAG = "DashboardActivity";
    private static final long INTERVAL = 60000;
    private static final long FASTEST_INTERVAL = 60000;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        initComponents();
        addListeners();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    void initComponents() {

        createLocationRequest();
        initGoogleApiClient();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    void addListeners() {
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            AppLog.e(TAG, "latitude - " + latitude + " longitude - " + longitude);
            saveLocation(latitude, longitude);
        }
    }

    private void saveLocation(double latitude, double longitude) {
        {
            try {
                List<LocationModel> locationModelList = new ArrayList<>();
                locationModelList.add(new LocationModel(latitude, longitude, "", "", System.currentTimeMillis()));
                new GenericSqliteAsyncTask(this)
                        .addQueryType(ADD_LOCATION)
                        .addContentValues(locationModelList)
                        .execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addMarkersOnMap(List<LocationModel> mLocationModels) {
        if (mMap != null) {
            if (mLocationModels != null && mLocationModels.size() > 0) {
                LatLng mLatLng = null;
                for (LocationModel model : mLocationModels) {
                    mLatLng = new LatLng(model.getLatitude(), model.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(mLatLng)
                            .title(sdf.format(model.getMillis()) + "\n" + model.getLocality())
                            .snippet(model.getCountry())
                            .draggable(true));
                }
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(mLatLng));
            }
        }
    }

    private void startLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_tracking_location:
                startLocationUpdates();
                break;
            case R.id.button_show_map:
                if (MyApplication.isDeviceConnectedToInternet()) {
                    new GenericSqliteAsyncTask(this)
                            .addQueryType(GET_ALL_LOCATION)
                            .execute();
                }
                break;
            case R.id.button_stop_tracking_location:
                stopLocationUpdates();
                break;

            case R.id.button_incoming_calls:
                startActivity(new Intent(v.getContext(), CallActivity.class)
                        .putExtra("call", ConstantCodes.INCOMING));
                break;
            case R.id.button_outgoing_calls:
                startActivity(new Intent(v.getContext(), CallActivity.class)
                        .putExtra("call", ConstantCodes.OUTGOING));
                break;
            case R.id.button_incoming_sms:
                startActivity(new Intent(v.getContext(), SmsActivity.class)
                        .putExtra("sms", ConstantCodes.INCOMING));
                break;
            case R.id.button_outgoing_sms:
                startActivity(new Intent(v.getContext(), SmsActivity.class)
                        .putExtra("sms", ConstantCodes.OUTGOING));
                break;
            case R.id.button_show_location:
                startActivity(new Intent(v.getContext(), LocationActivity.class));
                break;
        }
    }

    @Override
    public void onSqliteTaskPreExecute() {

    }

    @Override
    public void onSqliteTaskProgressUpdate(int queryType, Object values) {

    }

    @Override
    public void onSqliteTaskPostExecute(int queryType, Object result) {
        switch (queryType) {
            case GET_ALL_LOCATION:
                if (result != null) {
                    List<LocationModel> mLocationModels = (List<LocationModel>) result;
                    addMarkersOnMap(mLocationModels);
                }
                break;
        }
    }

    @Override
    public void onSqliteTaskCancelled(int queryType, Object result) {

    }
}