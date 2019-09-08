package com.arvis.nextcrops.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.event.GetSoilInfo;
import com.arvis.nextcrops.model.Estimate;
import com.arvis.nextcrops.model.Farm;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FarmLandInfoAdapter extends RecyclerView.Adapter<FarmLandInfoAdapter.SoilInfoViewHolder>{

    private List<Farm> farms = new ArrayList<>();

    private Map<String, Estimate> estimateMap = new HashMap<>();

    Context context;

    public FarmLandInfoAdapter(Context context){

        this.context = context;
    }

    @NonNull
    @Override
    public SoilInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SoilInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.soil_info, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SoilInfoViewHolder holder, int position) {

        Farm farm = farms.get(position);

        holder.landName.setText(farm.getName());

        Estimate estimate = estimateMap.get(farm.getId());

        if(estimate == null){

            EventBus.getDefault().post(new GetSoilInfo(farm.getId(), farm.getLat(), farm.getLon()));

        }else{

            holder.soilClass.setText(estimate.getAsc_oso().getText());

            holder.topSoilTexture.setText(estimate.getSoil_texture_topsoil().getText());

            holder.subSoilTexture.setText(estimate.getSoil_texture_subsoil().getText());

            holder.topSoilCP.setText(estimate.getClay().getTopsoil() + "%");

            holder.topSoilCPSD.setText(estimate.getClay().getTopsoil_stddev() + "%");

            holder.subSoildCP.setText(estimate.getClay().getSubsoil() + "%");

            holder.subSoilCPSD.setText(estimate.getClay().getSubsoil_stddev() + "%");

            holder.topSoilPh.setText(estimate.getPh().getTopsoil() + "%");

            holder.topSoilPhSD.setText(estimate.getPh().getTopsoil_stddev() + "%");

            holder.subSoilPh.setText(estimate.getPh().getSubsoil() + "%");

            holder.subSoilPhSD.setText(estimate.getPh().getSubsoil_stddev() + "%");

            holder.ocTopSoil.setText(estimate.getOrganic_carbon().getTopsoil() + "%");

            holder.ocTopSoilSD.setText(estimate.getOrganic_carbon().getTopsoil_stddev() + "%");

            holder.ocSubSoil.setText(estimate.getOrganic_carbon().getSubsoil() + "%");

            holder.ocSubSoilSD.setText(estimate.getOrganic_carbon().getSubsoil_stddev() + "%");

            holder.ecTopSoil.setText(estimate.getElectrical_conductivity().getTopsoil() + "%");

            holder.ecTopSoilSD.setText(estimate.getElectrical_conductivity().getTopsoil_stddev() + "%");

            holder.ecSubSoil.setText(estimate.getElectrical_conductivity().getSubsoil() + "%");

            holder.ecSubSoilSD.setText(estimate.getElectrical_conductivity().getSubsoil_stddev() + "%");

            holder.suggestedCrops.setText("Potato");

        }

    }

    @Override
    public int getItemCount() {
        return farms.size();
    }

    class SoilInfoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.land_name)
        TextView landName;

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

        public SoilInfoViewHolder(@NonNull View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public int addFarm(Farm farm){

        farms.add(farm);

        estimateMap.put(farm.getId(), null);

        notifyDataSetChanged();

        return farms.size() - 1;

    }

    public int updateFarmSoilEstimates(String id, Estimate estimate){

        estimateMap.put(id, estimate);

        notifyDataSetChanged();

        for(int i = 0; i < farms.size(); i++){

            if(farms.get(i).getId().equals(id)){

                return i;
            }
        }

        return 0;
    }

}
