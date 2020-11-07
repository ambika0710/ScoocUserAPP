package com.scooc.scooc.listeners;


import com.scooc.scooc.model.PromoCodeBean;
import com.scooc.scooc.model.UserBean;

public interface PromoCodeListener {

    void onLoadCompleted(PromoCodeBean promoCodeBean);

    void onLoadFailed(String error);

}
