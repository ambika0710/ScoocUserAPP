package com.scooc.scooc.listeners;


import com.scooc.scooc.model.UserBean;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.model.UserBean;

public interface UserInfoListener {

    void onLoadCompleted(UserBean userBean);

    void onLoadFailed(String error);

}
