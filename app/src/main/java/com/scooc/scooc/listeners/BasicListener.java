package com.scooc.scooc.listeners;

import com.scooc.scooc.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);

}
