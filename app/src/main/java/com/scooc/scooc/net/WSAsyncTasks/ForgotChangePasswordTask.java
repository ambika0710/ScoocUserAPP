package com.scooc.scooc.net.WSAsyncTasks;

import android.os.AsyncTask;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.net.invokers.ForgotChangePwdInvoker;
import com.scooc.scooc.net.invokers.LoginInvoker;

import org.json.JSONObject;


public class ForgotChangePasswordTask extends AsyncTask<String, Integer, BasicBean> {

    private ForgotChangePasswordTaskListener forgotChangePasswordTaskListener;

    private JSONObject postData;

    public ForgotChangePasswordTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        ForgotChangePwdInvoker forgotChangePwdInvoker = new ForgotChangePwdInvoker(null, postData);
        return forgotChangePwdInvoker.invokeLoginWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            forgotChangePasswordTaskListener.dataDownloadedSuccessfully(result);
        else
            forgotChangePasswordTaskListener.dataDownloadFailed();
    }

    public static interface ForgotChangePasswordTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public ForgotChangePasswordTaskListener getLoginTaskListener() {
        return forgotChangePasswordTaskListener;
    }

    public void setLoginTaskListener(ForgotChangePasswordTaskListener forgotChangePasswordTaskListener) {
        this.forgotChangePasswordTaskListener = forgotChangePasswordTaskListener;
    }
}
