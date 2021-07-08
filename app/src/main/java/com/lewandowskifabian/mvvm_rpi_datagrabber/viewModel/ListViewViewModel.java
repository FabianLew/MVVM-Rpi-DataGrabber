package com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.SensorDataModel;
import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerIoT;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListViewViewModel extends ViewModel {
    public ServerIoT serverIoT;

    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();

    public List<SensorDataModel> vmList;
    public MeasurementsAdapter adapter;

    public boolean btnState = true;

    public void Init(Context context){
        serverIoT = new ServerIoT(context.getApplicationContext());
        vmList = new ArrayList<>(6);
        adapter = new MeasurementsAdapter(vmList);
    }

    public void startTimer(){
        if(timer == null) {
            timer = new Timer();
            handleTimerTask();
            timer.schedule(timerTask, 0, 500);
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
                    refreshList();});
            }
        };
    }

    private void refreshList() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, serverIoT.getListUrl(), null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int rs = response.length();
                        int ms = vmList.size();
                        int sizeDiff = ms - rs;
                        for( int i = 0; i < sizeDiff; i++) {
                            vmList.remove(ms - 1 - i);
                            adapter.notifyItemRemoved(ms - 1 - i);
                        }
                        for (int i = 0; i < rs; i++) {
                            try {
                                /* get measurement model from JSON data */
                                SensorDataModel measurement = new SensorDataModel(response.getJSONObject(i));

                                /* update measurements list */
                                if(i >= ms) {
                                    vmList.add(measurement);
                                    adapter.notifyItemInserted(i);
                                } else {
                                    vmList.set(i, measurement);
                                    adapter.notifyItemChanged(i);
                                }
                                System.out.println(response);
                                //System.out.println(vmList.size());
                                System.out.println(adapter.getItemCount());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        serverIoT.queue.add(jsonObjectRequest);
    }

}
