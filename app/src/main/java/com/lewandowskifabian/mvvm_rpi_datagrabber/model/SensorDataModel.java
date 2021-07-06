package com.lewandowskifabian.mvvm_rpi_datagrabber.model;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorDataModel{
    private String name;
    private double value;
    private String unit;

    public SensorDataModel(String name, double value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    public SensorDataModel(JSONObject jsonObject){
        try {
            this.value = jsonObject.getInt("value");
            this.name = jsonObject.getString("name");
            this.unit = jsonObject.getString("unit");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return String.format("%.4f", value);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
