package com.scooc.scooc.listeners;

import com.scooc.scooc.model.TripCancellationBean;

public interface TripCancellationListener {

    void onLoadCompleted(TripCancellationBean tripCancellationBean);

    void onLoadFailed(String error);
}
