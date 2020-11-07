package com.scooc.scooc.listeners;

import com.scooc.scooc.model.TripListBean;

public abstract interface TripListListener {

    void onLoadCompleted(TripListBean tripListBean);

    void onLoadFailed(String error);

}
