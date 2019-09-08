package com.arvis.nextcrops.ui.vic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.CommError;
import com.arvis.nextcrops.event.GetSoilInfo;

import com.arvis.nextcrops.service.AVSApiService;
import com.arvis.nextcrops.ui.MenuActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VicMain extends MenuActivity {

    AVSApiService avsApiService = new AVSApiService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle("");
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.vic_main;
    }

    @Override
    protected void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {

        super.onStop();

        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getSoilEstimate(GetSoilInfo getSoilInfo){

        avsApiService.getSoilEstimates(getSoilInfo.id, getSoilInfo.lon, getSoilInfo.lat);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onError(CommError commError){

        new AlertDialog.Builder(this).setTitle("Error").setMessage(commError.error).setPositiveButton("Ok", null).create().show();
    }


}
