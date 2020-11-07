package com.scooc.scooc.listeners;


import com.scooc.scooc.model.LandingPageBean;
import com.scooc.scooc.model.TripListBean;

public interface LandingPageDetailsListener {

    void onLoadCompleted(LandingPageBean landingPageListBean);

    void onLoadFailed(String error);
}
