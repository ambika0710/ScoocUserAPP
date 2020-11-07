package com.scooc.scooc.listeners;


import com.scooc.scooc.model.TripDetailsBean;

public interface TripDetailsListener {

    void onLoadCompleted(TripDetailsBean tripDetailsBean);

    void onLoadFailed(String error);
}
