package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.model.CarBean;
import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.invokers.CarInfoInvoker;
import com.scooc.scooc.net.invokers.UserInfoInvoker;

public class CarInfoTask extends AsyncTask<String, Integer, CarBean> {

    private CarInfoTaskListener carInfoTaskListener;

    private HashMap<String, String> urlParams;

    public CarInfoTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    /*public CarInfoTask(JSONObject urlParams) {

    }*/

    @Override
    protected CarBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        CarInfoInvoker carInfoInvoker = new CarInfoInvoker(urlParams, null);
        return carInfoInvoker.invokeCarInfoWS();
    }

    @Override
    protected void onPostExecute(CarBean result) {
        if (result != null)
            carInfoTaskListener.dataDownloadedSuccessfully(result);
        else
            carInfoTaskListener.dataDownloadFailed();
    }

    public interface CarInfoTaskListener {
        void dataDownloadedSuccessfully(CarBean carBean);

        void dataDownloadFailed();
    }

    public CarInfoTaskListener getCarInfoTaskListener() {
        return carInfoTaskListener;
    }

    public void setCarInfoTaskListener(CarInfoTaskListener carInfoTaskListener) {
        this.carInfoTaskListener = carInfoTaskListener;
    }
}
