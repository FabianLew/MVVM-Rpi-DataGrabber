package com.lewandowskifabian.mvvm_rpi_datagrabber.model;


import android.util.Log;

import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.ServerDataViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerDataModel {
    private double data;

    public ServerDataViewModel toVM(){
        return new ServerDataViewModel(this);
    }

    public ServerDataModel(double data) {
        this.data = data;
    }
    public ServerDataModel(JSONObject json){
        try {
            this.data = json.getDouble("data");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("wrong JSON", "wrong JSON format");
        }
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
