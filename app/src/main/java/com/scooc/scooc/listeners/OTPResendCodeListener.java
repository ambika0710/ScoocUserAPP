package com.scooc.scooc.listeners;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.model.BasicBean;

public interface OTPResendCodeListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);
}
