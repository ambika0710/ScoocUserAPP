package com.scooc.scooc.listeners;


import com.scooc.scooc.model.CarBean;
import com.scooc.scooc.model.PlaceBean;

public interface PolyLineListener {

    void onLoadFailed(String error);

    void onLoadCompleted(PlaceBean placeBean);
}
