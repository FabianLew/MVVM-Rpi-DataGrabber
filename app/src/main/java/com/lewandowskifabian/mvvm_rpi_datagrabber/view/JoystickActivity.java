package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.enums.SenseTickDirections;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.JoystickModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class JoystickActivity extends AppCompatActivity {

    private View topStick, botStick, rightStick, leftStick, midStick;
    private final int grey = R.color.purple_500;
    private final int dimGrey = R.color.teal_700;
    private final int lightGrey = R.color.black;

    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();

    private ServerIoT serverIoT;
    private JoystickModel joystick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        topStick = (View)findViewById(R.id.topStick);
        botStick = (View)findViewById(R.id.botStick);
        rightStick = (View)findViewById(R.id.rightStick);
        leftStick = (View)findViewById(R.id.leftStick);
        midStick = (View)findViewById(R.id.midStick);

        serverIoT  = new ServerIoT("192.168.33.5", this);
        joystick = new JoystickModel();

        setLedViewColor(topStick, lightGrey);
        setLedViewColor(botStick, lightGrey);
        setLedViewColor(leftStick, lightGrey);
        setLedViewColor(rightStick, lightGrey);
        setLedViewColor(midStick, lightGrey);

        startTimer();

    }


    private void setLedViewColor(View v, int color){
        Drawable backgroundColor = v.getBackground();
        if (backgroundColor instanceof ShapeDrawable) {
            ((ShapeDrawable)backgroundColor).getPaint().setColor(color);
        } else if (backgroundColor instanceof GradientDrawable) {
            ((GradientDrawable)backgroundColor).setColor(color);
        } else if (backgroundColor instanceof ColorDrawable) {
            ((ColorDrawable)backgroundColor).setColor(color);
        }
    }

    private void indicateChange(JoystickModel joystick){
        switch (joystick.getDirection()) {
            case up :
                switch (joystick.getAction()){
                    case pressed:
                        setLedViewColor(topStick, grey);
                        break;
                    case held:
                        setLedViewColor(topStick, dimGrey);
                        break;
                    case released:
                        setLedViewColor(topStick, lightGrey);
                        break;
                }
                break;
            case down :
                switch (joystick.getAction()){
                    case pressed:
                        setLedViewColor(botStick, grey);
                        break;
                    case held:
                        setLedViewColor(botStick, dimGrey);
                        break;
                    case released:
                        setLedViewColor(botStick, lightGrey);
                        break;

                }
                break;
            case right :
                switch (joystick.getAction()){
                    case pressed:
                        setLedViewColor(rightStick, grey);
                        break;
                    case held:
                        setLedViewColor(rightStick, dimGrey);
                        break;
                    case released:
                        setLedViewColor(rightStick, lightGrey);
                        break;
                }
                break;
            case left :
                switch (joystick.getAction()){
                    case pressed:
                        setLedViewColor(leftStick, grey);
                        break;
                    case held:
                        setLedViewColor(leftStick, dimGrey);
                        break;
                    case released:
                        setLedViewColor(leftStick, lightGrey);
                        break;
                }
                break;
            case middle :
                switch (joystick.getAction()){
                    case pressed:
                        setLedViewColor(midStick, grey);
                        break;
                    case held:
                        setLedViewColor(midStick, dimGrey);
                        break;
                    case released:
                        setLedViewColor(midStick, lightGrey);
                        break;
                }
                break;
        }
    }

    private void sendGetRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, serverIoT.getJoystickUtl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //joystick.setParams(response);
                        indicateChange(new JoystickModel(response));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("errorMessage");
                    }
                });
        serverIoT.queue.add(jsonObjectRequest);
    }

    public void startTimer(){
        if(timer == null) {
            timer = new Timer();
            handleTimerTask();
            timer.schedule(timerTask, 0, 40);
        }
    }

    public void stopTimer(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void handleTimerTask(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post( () -> {
                    sendGetRequest();});
            }
        };
    }

}