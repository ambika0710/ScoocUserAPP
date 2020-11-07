package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import com.scooc.scooc.model.DriverBean;
import com.scooc.scooc.net.invokers.DriverDetailsInvoker;

public class DriverDetailsTask extends AsyncTask<String, Integer, DriverBean> {

    private DriverDetailsTaskListener driverDetailsTaskListener;

    private HashMap<String, String> urlParams;

    public DriverDetailsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected DriverBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        DriverDetailsInvoker driverDetailsInvoker = new DriverDetailsInvoker(urlParams, null);
        return driverDetailsInvoker.invokeDriverDetailsWS();
    }

    @Override
    protected void onPostExecute(DriverBean result) {
        if (result != null)
            driverDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            driverDetailsTaskListener.dataDownloadFailed();
    }

    public interface DriverDetailsTaskListener {

        void dataDownloadedSuccessfully(DriverBean driverBean);

        void dataDownloadFailed();

    }

    public DriverDetailsTaskListener getDriverDetailsTaskListener() {
        return driverDetailsTaskListener;
    }

    public void setDriverDetailsTaskListener(DriverDetailsTaskListener driverDetailsTaskListener) {
        this.driverDetailsTaskListener = driverDetailsTaskListener;

    }
}
