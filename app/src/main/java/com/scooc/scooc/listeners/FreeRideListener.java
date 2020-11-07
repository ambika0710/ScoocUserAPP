package com.scooc.scooc.listeners;


import com.scooc.scooc.model.FreeRideBean;

public interface FreeRideListener {

    void onLoadCompleted(FreeRideBean freeRideBean);

    void onLoadFailed(String error);

}
