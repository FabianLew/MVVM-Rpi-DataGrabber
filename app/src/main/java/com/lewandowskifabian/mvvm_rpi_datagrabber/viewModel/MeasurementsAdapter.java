package com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.databinding.MeasurementObjectBinding;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.SensorDataModel;

import java.util.List;

public class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MeasurementsViewHolder> {


    public static class MeasurementsViewHolder extends RecyclerView.ViewHolder{

        public MeasurementObjectBinding binding;

        public MeasurementsViewHolder(MeasurementObjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SensorDataModel measurement) {
            binding.setMeasurement(measurement);
            binding.executePendingBindings();
        }

    }

    private List<SensorDataModel> mMeasurements;

    public MeasurementsAdapter(List<SensorDataModel> mMeasurements) {
        this.mMeasurements = mMeasurements;
    }

    @NonNull
    @Override
    public MeasurementsAdapter.MeasurementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.measurement_object, parent, false);

        MeasurementObjectBinding measurementObjectBinding = MeasurementObjectBinding.inflate(inflater, parent,false);
        return new MeasurementsViewHolder(measurementObjectBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementsViewHolder holder, int position) {
        SensorDataModel measurementModel = mMeasurements.get(position);
        holder.bind(measurementModel);
    }

    @Override
    public int getItemCount() {
        return mMeasurements.size();
    }

}
