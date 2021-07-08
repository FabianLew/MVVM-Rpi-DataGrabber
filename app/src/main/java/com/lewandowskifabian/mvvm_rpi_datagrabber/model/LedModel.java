package com.lewandowskifabian.mvvm_rpi_datagrabber.model;

import android.content.Intent;

public class LedModel {
    public Integer R,G,B;

    public LedModel() {
        R = null;
        G = null;
        B = null;
    }

    public void clear(){
        R = null;
        G = null;
        B = null;
    }

    public void clearColor(){
        R = 0;
        G = 0;
        B = 0;
    }


    private int getR(){
        if(R != null)
            return R;
        else
            return 0;
    }


    private int getG(){
        if(G != null)
            return G;
        else
            return 0;
    }


    private int getB(){
        if(B != null)
            return B;
        else
            return 0;
    }

    public int getColor() {
        int r = getR();
        int g = getG();
        int b = getB();
        int a = (r + g + b) / 3;
        return  ((a & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) <<  8) | (b & 0xff);
    }

    public void setColor(LedModel mdl) {
        R = mdl.getR();
        G = mdl.getG();
        B = mdl.getB();
    }

    public boolean colorNotNull(){
        return (R != null) & (G != null) & (B != null);
    }
}
