package com.arvis.nextcrops.ui.queensland;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.CommError;
import com.arvis.nextcrops.event.Soil;
import com.arvis.nextcrops.event.StartAnalysisSamples;
import com.arvis.nextcrops.service.AVSApiService;
import com.arvis.nextcrops.service.QLDApiService;
import com.arvis.nextcrops.ui.MenuActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class QueenslandMain extends MenuActivity {

    private QLDApiService apiService;

    private static final int SAMPLE_RAD = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle("");
        }

        apiService = new QLDApiService(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.queensland_main;
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void startSampleAnalysis(StartAnalysisSamples startAnalysisSamples){

        EventBus.getDefault().removeStickyEvent(startAnalysisSamples);

        apiService.getSoilChemSamples(startAnalysisSamples.lat, startAnalysisSamples.lon, SAMPLE_RAD);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onError(CommError commError){

        new AlertDialog.Builder(this).setTitle("Error").setMessage(commError.error).setPositiveButton("Ok", null).create().show();
    }
}
