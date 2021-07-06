package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.SensorDataModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;
import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.ListViewViewModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.MeasurementsAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListViewActivity extends AppCompatActivity {

    private ListViewViewModel viewModel;
    private RecyclerView recyclerView;
    private Button startButton, prevButton, nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        viewModel = new ViewModelProvider(this).get(ListViewViewModel.class);
        viewModel.Init(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        startButton = (Button) findViewById(R.id.startBtn);
        nextButton = (Button) findViewById(R.id.nextBtn);
        prevButton = (Button) findViewById(R.id.prevBtn);


        viewModel.adapter = new MeasurementsAdapter(viewModel.vmList);
        recyclerView.setAdapter(viewModel.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.btnState) {
                    viewModel.startTimer();
                    viewModel.btnState = false;
                    startButton.setText("Stop");
                }
                else{
                    viewModel.stopTimer();
                    viewModel.btnState = true;
                    startButton.setText("Start");
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListViewActivity.this, LedDisplayActivity.class);
                startActivity(intent);
            }
        });
    }

}