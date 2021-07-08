package com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;
import com.lewandowskifabian.mvvm_rpi_datagrabber.VolleyCallBack;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.JoystickModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JoystickViewModel extends ViewModel {
    private final int grey = R.color.purple_500;
    private final int dimGrey = R.color.red;
    private final int lightGrey = R.color.black;

    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();

    private ServerIoT serverIoT;

    public boolean buttonState = true;
    JoystickModel joystick;

    public void Init(Context context, View topStick, View botStick, View rightStick, View leftStick, View midStick){
        serverIoT  = new ServerIoT(context.getApplicationContext());

        setLedViewColor(topStick, lightGrey);
        setLedViewColor(botStick, lightGrey);
        setLedViewColor(leftStick, lightGrey);
        setLedViewColor(rightStick, lightGrey);
        setLedViewColor(midStick, lightGrey);

        joystick = new JoystickModel();

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

    private void indicateChange(JoystickModel joystick, View topStick, View botStick, View rightStick, View leftStick, View midStick){
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

    private void sendGetRequest(final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, serverIoT.getJoystickUtl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        joystick.setParams(response);
                        callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("errorMessage");
                    }
                });
        serverIoT.queue.add(jsonObjectRequest);
    }


    public void startTimer(View topStick, View botStick, View rightStick, View leftStick, View midStick){
        if(timer == null) {
            timer = new Timer();
            handleTimerTask(topStick, botStick, rightStick, leftStick, midStick);
            timer.schedule(timerTask, 0, 10);
        }
    }

    public void stopTimer(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void handleTimerTask(View topStick, View botStick, View rightStick, View leftStick, View midStick){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post( () -> {
                    sendGetRequest(new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            indicateChange(joystick, topStick,botStick,rightStick,leftStick,midStick);
                        }
                    });});
            }
        };
    }

}
