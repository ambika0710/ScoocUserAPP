package com.scooc.scooc.listeners;


import com.scooc.scooc.model.AuthBean;

public interface LoginListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
