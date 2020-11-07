package com.scooc.scooc.listeners;

import com.scooc.scooc.model.LandingPageBean;

public interface LandingPageListener {

    void onLoadFailed(String error);

    void onLoadCompleted(LandingPageBean landingPageBean);

}
