package com.scooc.scooc.listeners;


import com.scooc.scooc.model.DriverRatingBean;

public interface DriverRatingListener {

    void onLoadCompleted(DriverRatingBean driverRatingBean);

    void onLoadFailed(String error);
}


