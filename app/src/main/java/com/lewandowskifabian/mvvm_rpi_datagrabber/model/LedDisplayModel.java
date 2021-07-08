package com.lewandowskifabian.mvvm_rpi_datagrabber.model;

import org.json.JSONArray;
import org.json.JSONException;

public class LedDisplayModel {
    public int ledX = 8;
    public int ledY = 8;
    private LedModel[][] model;

    public LedDisplayModel() {
        model = new LedModel[ledX][ledY];
        for (int i = 0; i < ledX; i++){
            for (int j = 0; j < ledY; j++){
                model[i][j] = new LedModel();
            }
        }
    }

    public void setLedModel(int i, int j, LedModel mdl){
        model[i][j].setColor(mdl);
    }

    public void clearModel() {
        for(int i = 0; i < ledX; i++) {
            for (int j = 0; j < ledY; j++) {
                model[i][j].clear();
            }
        }
    }

    public void clearColor() {
        for(int i = 0; i < ledX; i++) {
            for (int j = 0; j < ledY; j++) {
                model[i][j].clearColor();
            }
        }
    }

    private JSONArray indexToJsonArray(int x, int y) {
        JSONArray array = new JSONArray();
        try {
            array.put(0, x);
            array.put(1, y);
            array.put(2, model[x][y].R);
            array.put(3, model[x][y].G);
            array.put(4, model[x][y].B);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public JSONArray clearJsonArray(int x, int y){
        JSONArray array = new JSONArray();
        clearColor();
        try{
                array.put(0, x);
                array.put(1, y);
                array.put(2, model[x][y].R);
                array.put(3, model[x][y].G);
                array.put(4, model[x][y].B);
        }
        catch (JSONException e) {
                e.printStackTrace();
        }
        return array;
    }

    public JSONArray getControlJsonArrayNull() {
        int led_n = 0;
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < ledX; i++) {
            for (int j = 0; j < ledY; j++) {
                try {
                    jsonArray.put(led_n, clearJsonArray(i,j));
                    led_n++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray;
    }

    public JSONArray getControlJsonArray() {
        int led_n = 0;
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < ledX; i++) {
            for (int j = 0; j < ledY; j++) {
                if(model[i][j].colorNotNull()) {
                    try {
                        jsonArray.put(led_n, indexToJsonArray(i, j));
                        led_n++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonArray;
    }
}
