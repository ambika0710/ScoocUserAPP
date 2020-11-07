package com.scooc.scooc.listeners;

import com.scooc.scooc.model.LocationBean;

public interface SavedLocationListener {

    void onLoadCompleted(LocationBean locationBean);

    void onLoadFailed(String error);

}
