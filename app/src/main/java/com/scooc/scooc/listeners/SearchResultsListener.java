package com.scooc.scooc.listeners;


import com.scooc.scooc.model.SearchResultsBean;

public interface SearchResultsListener {

    void onLoadCompleted(SearchResultsBean searchResultsBean);

    void onLoadFailed(String webErrorMsg);
}
