package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.GraphsActivityViewModel;

public class GraphsActivity extends AppCompatActivity {

    private GraphsActivityViewModel viewModel;
    private GraphView pressureGraph, humidityGraph, temperatureGraph, yawGraph, pitchGraph, rollGraph;
    private Button changeStateButton, configButton, nextViewButton;
    private TextView sampleTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        //Graphs initialization
        pressureGraph = (GraphView)findViewById(R.id.pressureGraph);
        humidityGraph = (GraphView)findViewById(R.id.humidityGraph);
        temperatureGraph = (GraphView)findViewById(R.id.temperatureGraph);
        yawGraph = (GraphView)findViewById(R.id.yawGraph);
        pitchGraph = (GraphView)findViewById(R.id.pitchGraph);
        rollGraph = (GraphView)findViewById(R.id.rollGraph);

        //Buttons initialization
        changeStateButton = (Button)findViewById(R.id.stateButton);
        configButton = (Button)findViewById(R.id.configButton);
        nextViewButton = (Button)findViewById(R.id.nextViewButton);

        //Text Views
        sampleTime = (TextView)findViewById(R.id.sampleTimeText);


        viewModel = new ViewModelProvider(this).get(GraphsActivityViewModel.class);
        sampleTime.setText(viewModel.setSampleTimeText());

        changeStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.buttonState) {
                    viewModel.Init(GraphsActivity.this, pressureGraph, humidityGraph, temperatureGraph, yawGraph, pitchGraph, rollGraph);
                    changeStateButton.setText("STOP");
                    viewModel.buttonState = false;
                }
                else{
                    viewModel.stopTimer();
                    changeStateButton.setText("START");
                    viewModel.buttonState = true;
                }
            }
        });

        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfigActivity();
            }
        });

        nextViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphsActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openConfigActivity() {
        Intent intent = new Intent(this, ConfigActivity.class);
        Bundle configBundle = new Bundle();
        configBundle.putString("ip", viewModel.getIp());
        configBundle.putInt("sampleTime", viewModel.getSampleTime());
        intent.putExtras(configBundle);
        mGetContent.launch(intent);
    }


    private ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        String newSampleTime = data.getStringExtra("sampleTime");
                        String newIp = data.getStringExtra("ip");
                        viewModel.setIp(newIp);
                        viewModel.setSampleTime(Integer.parseInt(newSampleTime));
                        sampleTime.setText(viewModel.setSampleTimeText());
                    }
                }
            });
}