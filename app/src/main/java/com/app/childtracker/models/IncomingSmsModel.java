package com.app.childtracker.models;

/**
 * Created by plalit on 5/31/2016.
 */
public class IncomingSmsModel {

    private String number;
    private String message;
    private long millis;

    public IncomingSmsModel(String number, String message, long millis){
        this.number = number;
        this.message = message;
        this.millis = millis;
    }

    public String getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    public long getMillis() {
        return millis;
    }
}
