package com.scooc.scooc.listeners;

import com.scooc.scooc.model.RequestBean;

public interface RequestRideListener {

    void onLoadCompleted(RequestBean requestBean);

    void onLoadFailed(String error);
}
