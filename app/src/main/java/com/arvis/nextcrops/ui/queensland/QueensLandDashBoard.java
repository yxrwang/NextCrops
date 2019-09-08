package com.arvis.nextcrops.ui.queensland;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.SoilChemSample;
import com.arvis.nextcrops.event.StartAnalysisSamples;
import com.arvis.nextcrops.model.Farm;
import com.arvis.nextcrops.ui.AddFarm;
import com.arvis.nextcrops.ui.DashBoard;
import com.arvis.nextcrops.ui.view.SoilSampleDataAdapter;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class QueensLandDashBoard extends DashBoard {

    private SoilSampleDataAdapter soilSampleDataAdapter;

    @BindView(R.id.crops_suggest_panel)
    View farmInfo;

    @BindView(R.id.land_name)
    TextView farmName;

    @BindView(R.id.suggested_crops)
    TextView suggestedCrops;

    @Override
    public LatLng getCurrentLocation() {
        return new LatLng(-24.85371, 151.1602);
    }

    @Override
    protected int getViewLayout() {

        return R.layout.queensland_dash_board;
    }

    @Override
    protected void setAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        soilSampleDataAdapter = new SoilSampleDataAdapter(getActivity());

        recyclerView.setAdapter(soilSampleDataAdapter);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void startSampleAnalysis(StartAnalysisSamples startAnalysisSamples){

        createProgressDialog(R.string.progress_get_suggest_crops);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSampleAnalysised(SoilChemSample soilChemSample){

        EventBus.getDefault().removeStickyEvent(soilChemSample);

        removeProgressDialog();

        farmInfo.setVisibility(View.VISIBLE);

        soilSampleDataAdapter.setSoilSamples(soilChemSample.samples);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == AddFarm.REQ_ADD_FARM) {

            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {

                    Farm farm = data.getParcelableExtra(AddFarm.EXTRA_LAND);

                    if (farm != null && recyclerView != null){

                        farmName.setText(farm.getName());

                        EventBus.getDefault().postSticky(new StartAnalysisSamples(farm.getLat(), farm.getLon()));
                    }

                }

            }

        }else{

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
