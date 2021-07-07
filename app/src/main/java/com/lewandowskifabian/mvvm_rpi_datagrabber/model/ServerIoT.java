package com.lewandowskifabian.mvvm_rpi_datagrabber.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ServerIoT {
    public String ip;
    public RequestQueue queue;

    public ServerIoT(String ip, Context context) {
        this.ip = ip;
        this.queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public String getFileUrl(){
        return "http://" + ip + "/resource.php";
    }

    public String getListUrl(){
        return  "http://" + ip + "/datalist.json";
    }

    public String getLedScriptUrl(){
        return "http://" + ip + "/led_display.php";
    }

    public String getJoystickUtl(){
        return "http://" + ip + "/datajoystick123.json";
    }

    public void putControlRequest(JSONArray data) {
        JsonArrayRequest putRequest = new JsonArrayRequest(Request.Method.PUT, getLedScriptUrl(), data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        putRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(putRequest);
    }
}

