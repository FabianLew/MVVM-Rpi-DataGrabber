package com.lewandowskifabian.mvvm_rpi_datagrabber.model;

import android.util.Log;

import com.lewandowskifabian.mvvm_rpi_datagrabber.enums.SenseTickAction;
import com.lewandowskifabian.mvvm_rpi_datagrabber.enums.SenseTickDirections;

import org.json.JSONException;
import org.json.JSONObject;

public class JoystickModel {
    private SenseTickAction action;
    private SenseTickDirections direction;

    public JoystickModel() {
        action = null;
        direction = null;
    }

    public JoystickModel(JSONObject json){
        try {
            this.action = SenseTickAction.valueOf(json.getString("Action"));
            this.direction = SenseTickDirections.valueOf(json.getString("Direction"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Bad json", "Bad JSON format");
        }

    }

    public void setParams(JSONObject json){
        try {
            this.action = SenseTickAction.valueOf(json.getString("Action"));
            this.direction = SenseTickDirections.valueOf(json.getString("Direction"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Bad json", "Bad JSON format");
        }
    }

    public SenseTickAction getAction() {
        return action;
    }

    public void setAction(SenseTickAction action) {
        this.action = action;
    }

    public SenseTickDirections getDirection() {
        return direction;
    }

    public void setDirection(SenseTickDirections direction) {
        this.direction = direction;
    }
}
