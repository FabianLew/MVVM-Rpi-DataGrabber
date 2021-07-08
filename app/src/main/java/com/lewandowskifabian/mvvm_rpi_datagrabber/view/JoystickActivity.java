package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.enums.SenseTickDirections;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.JoystickModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;
import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.JoystickViewModel;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class JoystickActivity extends AppCompatActivity {

    private View topStick, botStick, rightStick, leftStick, midStick;
    private JoystickViewModel viewModel;
    private Button startBtn, prevBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        topStick = (View)findViewById(R.id.topStick);
        botStick = (View)findViewById(R.id.botStick);
        rightStick = (View)findViewById(R.id.rightStick);
        leftStick = (View)findViewById(R.id.leftStick);
        midStick = (View)findViewById(R.id.midStick);

        startBtn = (Button)findViewById(R.id.startStopBtn);
        prevBtn = (Button)findViewById(R.id.prevActBtn);


        viewModel = new ViewModelProvider(this).get(JoystickViewModel.class);

        viewModel.Init(this, topStick, botStick,rightStick,leftStick,midStick);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.buttonState) {
                    viewModel.startTimer(topStick, botStick, rightStick, leftStick, midStick);
                    startBtn.setText("Stop");
                    viewModel.buttonState = false;
                }
                else{
                    viewModel.stopTimer();
                    startBtn.setText("Start");
                    viewModel.buttonState = true;
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}