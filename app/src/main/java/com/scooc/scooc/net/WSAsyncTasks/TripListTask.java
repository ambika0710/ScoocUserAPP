package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import com.scooc.scooc.model.TripListBean;
import com.scooc.scooc.net.invokers.TripListInvoker;

public class TripListTask extends AsyncTask<String, Integer, TripListBean> {

    private TripListTaskListener tripListTaskListener;

    private HashMap<String, String> urlParams;

    public TripListTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripListBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        TripListInvoker tripListInvoker = new TripListInvoker(urlParams, null);
        return tripListInvoker.invokeTripsWS();

    }

    @Override
    protected void onPostExecute(TripListBean result) {
        super.onPostExecute(result);
        if (result != null)
            tripListTaskListener.dataDownloadedSuccessfully(result);
        else
            tripListTaskListener.dataDownloadFailed();

    }

    public static interface TripListTaskListener {

        void dataDownloadedSuccessfully(TripListBean tripListBean);

        void dataDownloadFailed();

    }

    public TripListTaskListener getTripListTaskListener() {
        return tripListTaskListener;
    }

    public void setTripListTaskListener(TripListTaskListener tripListTaskListener) {
        this.tripListTaskListener = tripListTaskListener;

    }
}
