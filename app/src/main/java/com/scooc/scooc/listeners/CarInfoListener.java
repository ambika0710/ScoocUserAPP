package com.scooc.scooc.listeners;


import com.scooc.scooc.model.CarBean;
import com.scooc.scooc.model.UserBean;

public interface CarInfoListener {

    void onLoadFailed(String error);

    void onLoadCompleted(CarBean carBean);

}
