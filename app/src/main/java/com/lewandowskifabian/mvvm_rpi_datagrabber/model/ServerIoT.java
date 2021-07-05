package com.lewandowskifabian.mvvm_rpi_datagrabber.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
}

