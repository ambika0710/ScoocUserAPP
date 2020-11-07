package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import com.scooc.scooc.model.SearchResultsBean;
import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.invokers.SearchResultsInvoker;
import com.scooc.scooc.net.invokers.UserInfoInvoker;

public class SearchResultsTask extends AsyncTask<String, Integer, SearchResultsBean> {

    private SearchResultsTaskListener searchResultsTaskListener;

    private HashMap<String, String> urlParams;

    public SearchResultsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected SearchResultsBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        SearchResultsInvoker searchResultsInvoker = new SearchResultsInvoker(urlParams, null);
        return searchResultsInvoker.invokeSearchresultsWS();
    }

    @Override
    protected void onPostExecute(SearchResultsBean result) {
        if (result != null)
            searchResultsTaskListener.dataDownloadedSuccessfully(result);
        else
            searchResultsTaskListener.dataDownloadFailed();
    }

    public interface SearchResultsTaskListener {

        void dataDownloadedSuccessfully(SearchResultsBean userBean);

        void dataDownloadFailed();
    }

    public SearchResultsTaskListener getSearchResultsTaskListener() {
        return searchResultsTaskListener;
    }

    public void setSearchResultsTaskListener(SearchResultsTaskListener searchResultsTaskListener) {
        this.searchResultsTaskListener = searchResultsTaskListener;
    }
}
