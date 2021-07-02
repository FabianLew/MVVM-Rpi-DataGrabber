package com.lewandowskifabian.mvvm_rpi_datagrabber.model;


import com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel.ServerDataViewModel;

public class ServerDataModel {
    private double data;

    public ServerDataViewModel toVM(){
        return new ServerDataViewModel(this);
    }

    public ServerDataModel(double data) {
        this.data = data;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
