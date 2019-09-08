package com.arvis.nextcrops;

import android.app.Application;

import com.arvis.nextcrops.comm.HttpsClientService;

public class NextCropsApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        HttpsClientService.init(this);
    }
}
