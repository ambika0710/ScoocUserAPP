package com.scooc.scooc.listeners;


import com.scooc.scooc.model.DriverBean;

public interface DriverDetailsListener {

    void onLoadCompleted(DriverBean driverBean);

    void onLoadFailed(String error);
}
