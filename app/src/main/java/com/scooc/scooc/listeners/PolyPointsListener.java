package com.scooc.scooc.listeners;


import com.scooc.scooc.model.PolyPointsBean;

public interface PolyPointsListener {

    void onLoadFailed(String error);

    void onLoadCompleted(PolyPointsBean polyPointsBean);
}
