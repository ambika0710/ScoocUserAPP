package com.scooc.scooc.listeners;


import com.scooc.scooc.model.DriverBean;

public interface AppStatusListener {

    void onLoadFailed(String error);

    void onLoadCompleted(DriverBean driverBean);

}
