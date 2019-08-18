package com.pe.places.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class PlaceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setupStetho();
    }

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
