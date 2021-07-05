package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.databinding.ActivityGraphsBinding;
import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.GraphsActivityViewModel;

public class GraphsActivity extends AppCompatActivity {

    private ActivityGraphsBinding binding;
    private GraphsActivityViewModel viewModel;
    private GraphView pressureGraph, humidityGraph, temperatureGraph, yawGraph, pitchGraph, rollGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        pressureGraph = (GraphView)findViewById(R.id.pressureGraph);
        humidityGraph = (GraphView)findViewById(R.id.humidityGraph);
        temperatureGraph = (GraphView)findViewById(R.id.temperatureGraph);
        yawGraph = (GraphView)findViewById(R.id.yawGraph);
        pitchGraph = (GraphView)findViewById(R.id.pitchGraph);
        rollGraph = (GraphView)findViewById(R.id.rollGraph);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_graphs);

        viewModel = new ViewModelProvider(this).get(GraphsActivityViewModel.class);
        viewModel.Init(this, pressureGraph, humidityGraph, temperatureGraph, yawGraph, pitchGraph, rollGraph);

    }
}