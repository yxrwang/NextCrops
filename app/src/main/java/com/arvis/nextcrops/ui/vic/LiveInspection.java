package com.arvis.nextcrops.ui.vic;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.CommError;
import com.arvis.nextcrops.event.LocationDetermined;
import com.arvis.nextcrops.event.Soil;
import com.arvis.nextcrops.model.Estimate;
import com.arvis.nextcrops.service.AVSApiService;
import com.arvis.nextcrops.ui.AddFarm;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveInspection extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    public final static int REQ_LIVE_INSPECTION = 200;

    @BindView(R.id.live_location)
    TextView currentLocation;

    @BindView(R.id.progress_location)
    View progress;

    @BindView(R.id.info_panel)
    View infoPanel;

    @BindView(R.id.soil_class)
    TextView soilClass;

    @BindView(R.id.top_soil_texture)
    TextView topSoilTexture;

    @BindView(R.id.sub_soil_texture)
    TextView subSoilTexture;

    @BindView(R.id.clay_percentage_top_soil)
    TextView topSoilCP;

    @BindView(R.id.clay_percentage_top_soil_stddev)
    TextView topSoilCPSD;

    @BindView(R.id.clay_percentage_sub_soil)
    TextView subSoildCP;

    @BindView(R.id.clay_percentage_sub_soil_stddev)
    TextView subSoilCPSD;

    @BindView(R.id.ph_top_soil)
    TextView topSoilPh;

    @BindView(R.id.ph_top_soil_stddev)
    TextView topSoilPhSD;

    @BindView(R.id.ph_sub_soil)
    TextView subSoilPh;

    @BindView(R.id.ph_sub_soil_stddev)
    TextView subSoilPhSD;

    @BindView(R.id.oc_top_soil)
    TextView ocTopSoil;

    @BindView(R.id.oc_top_soil_stddev)
    TextView ocTopSoilSD;

    @BindView(R.id.oc_sub_soil)
    TextView ocSubSoil;

    @BindView(R.id.oc_sub_soil_stddev)
    TextView ocSubSoilSD;

    @BindView(R.id.ec_top_soil)
    TextView ecTopSoil;

    @BindView(R.id.ec_top_soil_stddev)
    TextView ecTopSoilSD;

    @BindView(R.id.ec_sub_soil)
    TextView ecSubSoil;

    @BindView(R.id.ec_sub_soil_stddev)
    TextView ecSubSoilSD;

    @BindView(R.id.suggested_crops)
    TextView suggestedCrops;

    AVSApiService avsApiService = new AVSApiService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){

            getSupportActionBar().hide();
        }

        setContentView(R.layout.live_inspection);

        ButterKnife.bind(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onResume() {

        super.onResume();

        EventBus.getDefault().register(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                fusedLocationClient.getLastLocation().addOnSuccessListener(LiveInspection.this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {

                        EventBus.getDefault().postSticky(new LocationDetermined(location));
                    }

                });
            }
        }, 5000);


    }

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    public void onLocationDetermined(LocationDetermined locationDetermined){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                currentLocation.setText(locationDetermined.location.getLatitude() + " " + locationDetermined.location.getLongitude());
            }
        });

        avsApiService.getSoilEstimates(UUID.randomUUID().toString(), locationDetermined.location.getLongitude(), locationDetermined.location.getLatitude());
    }

    @OnClick(R.id.btn_save_live_land)
    public void saveLiveLandInfo(View view){

        LocationDetermined locationDetermined = EventBus.getDefault().removeStickyEvent(LocationDetermined.class);

        Intent saveLand = getIntent();

        if(locationDetermined != null){

            saveLand.putExtra(AddFarm.EXTRA_LAT, locationDetermined.location.getLatitude());

            saveLand.putExtra(AddFarm.EXTRA_LON, locationDetermined.location.getLongitude());
        }

        setResult(RESULT_OK, saveLand);

        finish();

    }

    @OnClick(R.id.btn_quit)
    public void closeLiveInspection(View view){

        EventBus.getDefault().removeStickyEvent(LocationDetermined.class);

        finish();
    }

    @Override
    protected void onStop() {

        super.onStop();

        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onBackPressed() {

        EventBus.getDefault().removeStickyEvent(LocationDetermined.class);

        finish();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSoilInfoGot(Soil soil) {

        EventBus.getDefault().removeStickyEvent(soil);

        progress.setVisibility(View.GONE);

        showSoilInfo(soil.estimate);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onError(CommError commError) {

        EventBus.getDefault().removeStickyEvent(commError);

        new AlertDialog.Builder(this).setTitle(R.string.error).setMessage(commError.error).setPositiveButton(R.string.ok, null).create().show();
    }


    private void showSoilInfo(Estimate estimate){

        if(estimate != null){

            soilClass.setText(estimate.getAsc_oso().getText());

            topSoilTexture.setText(estimate.getSoil_texture_topsoil().getText());

            subSoilTexture.setText(estimate.getSoil_texture_subsoil().getText());

            topSoilCP.setText(estimate.getClay().getTopsoil() + "%");

            topSoilCPSD.setText(estimate.getClay().getTopsoil_stddev() + "%");

            subSoildCP.setText(estimate.getClay().getSubsoil() + "%");

            subSoilCPSD.setText(estimate.getClay().getSubsoil_stddev() + "%");

            topSoilPh.setText(estimate.getPh().getTopsoil() + "%");

            topSoilPhSD.setText(estimate.getPh().getTopsoil_stddev() + "%");

            subSoilPh.setText(estimate.getPh().getSubsoil() + "%");

            subSoilPhSD.setText(estimate.getPh().getSubsoil_stddev() + "%");

            ocTopSoil.setText(estimate.getOrganic_carbon().getTopsoil() + "%");

            ocTopSoilSD.setText(estimate.getOrganic_carbon().getTopsoil_stddev() + "%");

            ocSubSoil.setText(estimate.getOrganic_carbon().getSubsoil() + "%");

            ocSubSoilSD.setText(estimate.getOrganic_carbon().getSubsoil_stddev() + "%");

            ecTopSoil.setText(estimate.getElectrical_conductivity().getTopsoil() + "%");

            ecTopSoilSD.setText(estimate.getElectrical_conductivity().getTopsoil_stddev() + "%");

            ecSubSoil.setText(estimate.getElectrical_conductivity().getSubsoil() + "%");

            ecSubSoilSD.setText(estimate.getElectrical_conductivity().getSubsoil_stddev() + "%");

            suggestedCrops.setText("Potato");

            infoPanel.setVisibility(View.VISIBLE);
        }
    }

}
