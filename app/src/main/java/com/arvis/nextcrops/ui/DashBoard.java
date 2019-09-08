package com.arvis.nextcrops.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.CommError;
import com.arvis.nextcrops.event.GetSoilInfo;
import com.arvis.nextcrops.event.Soil;
import com.arvis.nextcrops.model.Farm;
import com.arvis.nextcrops.ui.vic.LiveInspection;
import com.arvis.nextcrops.ui.view.FarmLandInfoAdapter;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

public class DashBoard extends ArvisFragment {

    private FarmLandInfoAdapter farmLandInfoAdapter;

    @BindView(R.id.soil_info_list)
    protected RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    protected int getViewLayout() {

        return R.layout.vic_dashboard;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(recyclerView != null){

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

            setAdapter();
        }


    }

    protected void setAdapter(){

        farmLandInfoAdapter = new FarmLandInfoAdapter(getContext());

        recyclerView.setAdapter(farmLandInfoAdapter);
    }

    @Optional
    @OnClick(R.id.btn_live_inspection)
    public void liveInspection(View view){

        startActivityForResult(new Intent(getActivity(), LiveInspection.class), LiveInspection.REQ_LIVE_INSPECTION);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSoilInfo(GetSoilInfo getSoilInfo) {

        createProgressDialog(R.string.progress_get_soil_info);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSoilInfoGot(Soil soil) {

        EventBus.getDefault().removeStickyEvent(soil);

        removeProgressDialog();

        if(recyclerView != null)
            recyclerView.scrollToPosition(farmLandInfoAdapter.updateFarmSoilEstimates(soil.id, soil.estimate));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onError(CommError commError) {

        EventBus.getDefault().removeStickyEvent(commError);

        removeProgressDialog();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.add_land){

            Intent addLand = new Intent(getActivity(), AddFarm.class);

            LatLng location = getCurrentLocation();

            if(location != null){

                addLand.putExtra(AddFarm.EXTRA_LAT, location.latitude);

                addLand.putExtra(AddFarm.EXTRA_LON, location.longitude);
            }

            startActivityForResult(addLand, AddFarm.REQ_ADD_FARM);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == AddFarm.REQ_ADD_FARM) {

            if (resultCode == Activity.RESULT_OK) {

                if (data != null) {

                    Farm farm = data.getParcelableExtra(AddFarm.EXTRA_LAND);

                    if (farm != null && recyclerView != null)
                        recyclerView.scrollToPosition(farmLandInfoAdapter.addFarm(farm));
                }

            }

        }else if(requestCode == LiveInspection.REQ_LIVE_INSPECTION){

            if(resultCode == Activity.RESULT_OK){

                Intent saveFarm = new Intent(getActivity(), AddFarm.class);

                if(data != null){

                    saveFarm.putExtra(AddFarm.EXTRA_LAT, data.getDoubleExtra(AddFarm.EXTRA_LAT, Double.NaN));

                    saveFarm.putExtra(AddFarm.EXTRA_LON, data.getDoubleExtra(AddFarm.EXTRA_LON, Double.NaN));
                }

                startActivityForResult(saveFarm, AddFarm.REQ_ADD_FARM);
            }

        }else

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    public LatLng getCurrentLocation(){

        return new LatLng(-38.385436, 143.021835);
    }
}
