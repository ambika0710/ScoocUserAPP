package com.scooc.scooc.listeners;


import com.scooc.scooc.model.UserBean;

public interface EditProfileListener {

    void onLoadCompleted(UserBean userBean);

    void onLoadFailed(String error);

}
