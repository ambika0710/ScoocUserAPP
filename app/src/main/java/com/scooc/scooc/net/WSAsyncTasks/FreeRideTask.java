package com.scooc.scooc.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.model.FreeRideBean;
import com.scooc.scooc.net.invokers.FreeRideInvoker;
import com.scooc.scooc.net.invokers.LoginInvoker;


public class FreeRideTask extends AsyncTask<String, Integer, FreeRideBean> {

    private FreeRideTaskListener freeRideTaskListener;

    private JSONObject postData;

    public FreeRideTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected FreeRideBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        FreeRideInvoker freeRideInvoker = new FreeRideInvoker(null, postData);
        return freeRideInvoker.invokeFreeRideWS();
    }

    @Override
    protected void onPostExecute(FreeRideBean result) {
        super.onPostExecute(result);
        if (result != null)
            freeRideTaskListener.dataDownloadedSuccessfully(result);
        else
            freeRideTaskListener.dataDownloadFailed();
    }

    public static interface FreeRideTaskListener {

        void dataDownloadedSuccessfully(FreeRideBean freeRideBean);

        void dataDownloadFailed();
    }

    public FreeRideTaskListener getFreeRideTaskListener() {
        return freeRideTaskListener;
    }

    public void setFreeRideTaskListener(FreeRideTaskListener freeRideTaskListener) {
        this.freeRideTaskListener = freeRideTaskListener;
    }
}
