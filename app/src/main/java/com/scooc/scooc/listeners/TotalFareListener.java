package com.scooc.scooc.listeners;


import com.scooc.scooc.model.FareBean;

public interface TotalFareListener {

    void onLoadCompleted(FareBean fareBean);

    void onLoadFailed(String error);
}
