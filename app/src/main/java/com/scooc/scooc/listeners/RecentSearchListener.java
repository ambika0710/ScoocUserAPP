package com.scooc.scooc.listeners;

import com.scooc.scooc.model.RecentSearchBean;

public interface RecentSearchListener {

    void onLoadCompleted(RecentSearchBean recentSearchBean);

    void onLoadFailed(String webErrorMsg);
}
