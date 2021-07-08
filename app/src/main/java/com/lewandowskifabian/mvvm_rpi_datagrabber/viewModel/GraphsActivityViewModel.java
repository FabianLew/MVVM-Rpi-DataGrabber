package com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerDataModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class GraphsActivityViewModel extends ViewModel {
    //Server
    public ServerIoT serverIoT;

    //Timer
    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();
    private long currentRequestTime = 0;
    private int sampleTime = 1000;

    //Initialization of data series
    private LineGraphSeries<DataPoint> pressureData= new LineGraphSeries<>(new DataPoint[]{});
    private LineGraphSeries<DataPoint> humidityData= new LineGraphSeries<>(new DataPoint[]{});
    private LineGraphSeries<DataPoint> temperatureData= new LineGraphSeries<>(new DataPoint[]{});
    private LineGraphSeries<DataPoint> yawData= new LineGraphSeries<>(new DataPoint[]{});
    private LineGraphSeries<DataPoint>pitchData= new LineGraphSeries<>(new DataPoint[]{});
    private LineGraphSeries<DataPoint>rollData= new LineGraphSeries<>(new DataPoint[]{});

    public boolean buttonState = true; // Set true when button state is Start and false if its Stop
    private boolean firstInit = true;

    public void stopTimer(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void Init(Context context, GraphView graph1, GraphView graph2, GraphView graph3, GraphView graph4, GraphView graph5, GraphView graph6){
        if (firstInit) {
            configGraph(graph1, pressureData, "Pressure[mBar] / Time[s]", 10.0, 1300.0);
            configGraph(graph2, humidityData, "Humidity[%] / Time[s]", 10.0, 105.0);
            configGraph(graph3, temperatureData, "Temperature[degC] / Time[s]", 10.0, 107.0);
            configGraph(graph4, yawData, "Yaw[deg] / Time[s]", 10.0, 365.0);
            configGraph(graph5, pitchData, "Pitch[deg] / Time[s]", 10.0, 365.0);
            configGraph(graph6, rollData, "Roll[deg] / Time[s]", 10.0, 365.0);
            startTimer(graph1, graph2, graph3, graph4, graph5, graph6);
            firstInit = false;
        }
        else{
            startTimer(graph1, graph2, graph3, graph4, graph5, graph6);
        }
    }

    private void configGraph(GraphView graph, LineGraphSeries<DataPoint> lineGraphSeries, String title, double width, double height){
        graph.addSeries(lineGraphSeries);
        graph.setTitle(title);
        graph.getViewport().setMaxY(height);
        graph.getViewport().setMaxX(width);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
    }

    private void sendGetRequest(LineGraphSeries<DataPoint> lineGraphSeries, GraphView graph1, String url){
        String errorMessage = "sndGetRequest failed";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updatePlot(response, lineGraphSeries, graph1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(errorMessage);
                    }
                });
        serverIoT.queue.add(jsonObjectRequest);
    }


    private void updatePlot(JSONObject json, LineGraphSeries<DataPoint> lineGraphSeries, GraphView graph1) {
            if (timer != null) {
                double timeStamp = currentRequestTime / 1000.0;
                ServerDataViewModel data;
                data = new ServerDataModel(json).toVM();
                boolean scrollGraph = (timeStamp > 10.0);
                lineGraphSeries.appendData(new DataPoint(timeStamp, data.getData()), scrollGraph, 1000);
                graph1.onDataChanged(true, true);
            }
    }

    private void startTimer(GraphView graph1, GraphView graph2, GraphView graph3, GraphView graph4, GraphView graph5, GraphView graph6){
        if(timer == null) {
            timer = new Timer();
            updatePlotsWithServerResponse(graph1,graph2,graph3,graph4,graph5,graph6);
            timer.schedule(timerTask, 0, sampleTime);
        }
    }

    private void updatePlotsWithServerResponse(GraphView graph1, GraphView graph2, GraphView graph3, GraphView graph4, GraphView graph5, GraphView graph6){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post( () -> {
                    sendGetRequest(pressureData, graph1, serverIoT.getFileUrl3());
                    sendGetRequest(humidityData, graph2, serverIoT.getFileUrl());
                    sendGetRequest(temperatureData, graph3, serverIoT.getFileUrl2());
                    sendGetRequest(yawData,graph4, serverIoT.getFileUrl4());
                    sendGetRequest(pitchData,graph5, serverIoT.getFileUrl5());
                    sendGetRequest(rollData,graph6, serverIoT.getFileUrl6());
                    currentRequestTime += sampleTime;
                });
            }
        };
    }

    public String setSampleTimeText(){
        return "Sample time: " + String.valueOf(sampleTime) + "ms";
    }


    public void setIp(String ip) {
        serverIoT.ip = ip;
    }

    public int getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(int sampleTime) {
        this.sampleTime = sampleTime;
    }
}
