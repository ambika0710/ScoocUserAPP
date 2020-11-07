package com.scooc.scooc.listeners;


import com.scooc.scooc.model.SuccessBean;

public interface SuccessListener {

    void onLoadCompleted(SuccessBean successBean);

    void onLoadFailed(String error);
}
