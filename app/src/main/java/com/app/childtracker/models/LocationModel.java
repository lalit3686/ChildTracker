package com.app.childtracker.models;

/**
 * Created by Lalit T. Poptani on 12/03/2016.
 */
public class LocationModel {
    private double latitude;
    private double longitude;
    private String locality;
    private String country;
    private long millis;

    public LocationModel(double latitude, double longitude, String locality, String country, long millis){
        this.latitude = latitude;
        this.longitude = longitude;
        this.locality = locality;
        this.country = country;
        this.millis = millis;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocality() {
        return locality;
    }

    public String getCountry() {
        return country;
    }

    public long getMillis() {
        return millis;
    }
}
