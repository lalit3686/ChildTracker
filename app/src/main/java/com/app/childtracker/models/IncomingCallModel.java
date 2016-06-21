package com.app.childtracker.models;

/**
 * Created by plalit on 5/31/2016.
 */
public class IncomingCallModel {

    private String number;
    private long millis;


    public IncomingCallModel(String number, long millis){
        this.number = number;
        this.millis = millis;
    }

    public String getNumber() {
        return number;
    }

    public long getMillis() {
        return millis;
    }
}
