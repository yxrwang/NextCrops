package com.arvis.nextcrops.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arvis.nextcrops.R;
import com.arvis.nextcrops.model.Sample;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoilSampleDataAdapter extends RecyclerView.Adapter<SoilSampleDataAdapter.SoilSampleViewHolder> {

    List<Sample> samples = new ArrayList<>();

    Context context;

    public SoilSampleDataAdapter(Context context){

        this.context = context;
    }

    @NonNull
    @Override
    public SoilSampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SoilSampleViewHolder(LayoutInflater.from(context).inflate(R.layout.qld_soil_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SoilSampleViewHolder holder, int position) {

        Sample sample = samples.get(position);

        holder.projectCode.setText(sample.getProjectCode());

        holder.sampleNumber.setText(sample.getSampleNumber() + "");

        holder.labCode.setText(sample.getLabCode());

        holder.labMethodCode.setText(sample.getLabMethodCode());

        holder.analysisDate.setText(sample.getAnalysisDate());

        holder.resultStatus.setText(sample.getResultStatus());
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }

    class SoilSampleViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.project_code)
        TextView projectCode;

        @BindView(R.id.sample_number)
        TextView sampleNumber;

        @BindView(R.id.lab_code)
        TextView labCode;

        @BindView(R.id.lab_method_code)
        TextView labMethodCode;

        @BindView(R.id.analysis_date)
        TextView analysisDate;

        @BindView(R.id.result_status)
        TextView resultStatus;

        public SoilSampleViewHolder(@NonNull View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void setSoilSamples(List<Sample> soilSamples){

        this.samples = soilSamples;

        notifyDataSetChanged();
    }
}
