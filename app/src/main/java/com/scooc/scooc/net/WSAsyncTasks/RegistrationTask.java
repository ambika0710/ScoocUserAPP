package com.scooc.scooc.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.net.invokers.RegistrationInvoker;

public class RegistrationTask extends AsyncTask<String, Integer, AuthBean> {

    private RegistrationTaskListener registrationTaskListener;

    private JSONObject postData;

    public RegistrationTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected AuthBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RegistrationInvoker registrationInvoker = new RegistrationInvoker(null, postData);
        return registrationInvoker.invokeRegistrationWS();
    }

    @Override
    protected void onPostExecute(AuthBean result) {
        super.onPostExecute(result);
        if (result != null)
            registrationTaskListener.dataDownloadedSuccessfully(result);
        else
            registrationTaskListener.dataDownloadFailed();
    }

    public static interface RegistrationTaskListener {

        void dataDownloadedSuccessfully(AuthBean authBean);

        void dataDownloadFailed();
    }

    public RegistrationTaskListener getRegistrationTaskListener() {
        return registrationTaskListener;
    }

    public void setRegistrationTaskListener(RegistrationTaskListener registrationTaskListener) {
        this.registrationTaskListener = registrationTaskListener;
    }
}
