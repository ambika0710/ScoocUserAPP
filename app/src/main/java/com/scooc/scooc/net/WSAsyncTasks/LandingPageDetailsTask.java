package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import com.scooc.scooc.model.CarBean;
import com.scooc.scooc.model.LandingPageBean;
import com.scooc.scooc.net.invokers.CarInfoInvoker;
import com.scooc.scooc.net.invokers.LandingPageDetailsInvoker;

public class LandingPageDetailsTask extends AsyncTask<String, Integer, LandingPageBean> {

    private LandingPageDetailsTaskListener landingPageDetailsTaskListener;

    private HashMap<String, String> urlParams;

    public LandingPageDetailsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected LandingPageBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        LandingPageDetailsInvoker landingPageDetailsInvoker = new LandingPageDetailsInvoker(urlParams, null);
        return landingPageDetailsInvoker.invokeLandingPageDetailsWS();
    }

    @Override
    protected void onPostExecute(LandingPageBean result) {
        if (result != null)
            landingPageDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            landingPageDetailsTaskListener.dataDownloadFailed();
    }

    public interface LandingPageDetailsTaskListener {

        void dataDownloadedSuccessfully(LandingPageBean landingPageBean);

        void dataDownloadFailed();
    }

    public LandingPageDetailsTaskListener getLandingPageDetailsTaskListener() {
        return landingPageDetailsTaskListener;
    }

    public void setLandingPageDetailsTaskListener(LandingPageDetailsTaskListener landingPageDetailsTaskListener) {
        this.landingPageDetailsTaskListener = landingPageDetailsTaskListener;
    }
}
