package com.lewandowskifabian.mvvm_rpi_datagrabber.viewModel;

import androidx.lifecycle.ViewModel;

import com.lewandowskifabian.mvvm_rpi_datagrabber.model.ServerDataModel;


public class ServerDataViewModel extends ViewModel {
    private ServerDataModel model;

    public ServerDataViewModel(ServerDataModel model) {
        this.model = model;
    }

    public double getData(){
        return model.getData();
    }
}
