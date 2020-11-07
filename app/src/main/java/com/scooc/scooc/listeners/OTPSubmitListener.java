package com.scooc.scooc.listeners;

import com.scooc.scooc.model.OTPBean;
import com.scooc.scooc.model.PromoCodeBean;



public interface OTPSubmitListener {

    void onLoadCompleted(OTPBean otpBean);

    void onLoadFailed(String error);

}
