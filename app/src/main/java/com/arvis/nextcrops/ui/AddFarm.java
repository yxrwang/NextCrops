package com.arvis.nextcrops.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.LocationSelected;
import com.arvis.nextcrops.model.Farm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFarm extends AppCompatActivity implements OnMapReadyCallback {

    public static final int REQ_ADD_FARM = 100;

    public static final String EXTRA_LAND = "land";

    public static final String EXTRA_LAT = "lat";

    public static final String EXTRA_LON = "lon";

    private GoogleMap map;

    @BindView(R.id.input_farm_name)
    EditText farmName;

    @BindView(R.id.farm_location)
    EditText location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setTitle(getString(R.string.add_farm));
        }

        setContentView(R.layout.add_farm);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if(mapFragment != null){

            mapFragment.getMapAsync(this);
        }


        ButterKnife.bind(this);

        double existingLat = getIntent().getDoubleExtra(EXTRA_LAT, Double.NaN);

        double existingLon = getIntent().getDoubleExtra(EXTRA_LON, Double.NaN);

        if(!Double.isNaN(existingLat) && !Double.isNaN(existingLon)){

            EventBus.getDefault().postSticky(new LocationSelected(existingLat, existingLon));
        }
    }

    @OnClick(R.id.btn_cancel)
    public void cancelAddingFarm(View view){

        EventBus.getDefault().removeStickyEvent(LocationSelected.class);

        setResult(RESULT_CANCELED);

        finish();
    }

    @Override
    public void onBackPressed() {

        EventBus.getDefault().removeStickyEvent(LocationSelected.class);

        setResult(RESULT_CANCELED);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){

            EventBus.getDefault().removeStickyEvent(LocationSelected.class);

            setResult(RESULT_CANCELED);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_save_farm)
    public void saveFarm(View view){

        if(TextUtils.isEmpty(farmName.getText())){

            new AlertDialog.Builder(this).setTitle(R.string.error).setMessage(R.string.error_missing_farm_name).setPositiveButton(R.string.ok, null).create().show();

            return;
        }

        if(TextUtils.isEmpty(location.getText())){

            new AlertDialog.Builder(this).setTitle(R.string.error).setMessage(R.string.error_missing_location).setPositiveButton(R.string.ok, null).create().show();

            return;
        }

        LocationSelected locationSelected = EventBus.getDefault().removeStickyEvent(LocationSelected.class);

        Intent farmData = getIntent();

        farmData.putExtra(EXTRA_LAND, new Farm(farmName.getText().toString(), locationSelected.lat, locationSelected.lon));

        setResult(RESULT_OK, farmData);

        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;

        map.setMyLocationEnabled(true);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        LocationSelected locationSelected = EventBus.getDefault().getStickyEvent(LocationSelected.class);

        if(locationSelected != null){

            map.addMarker(new MarkerOptions().position(new LatLng(locationSelected.lat, locationSelected.lon)));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationSelected.lat, locationSelected.lon), 12));


        }

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {

                EventBus.getDefault().postSticky(new LocationSelected(latLng.latitude, latLng.longitude));

                map.clear();

                map.addMarker(new MarkerOptions().position(latLng));

                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void locationSelected(LocationSelected locationSelected){

        location.setText(locationSelected.lat + " " + locationSelected.lon);
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
}
