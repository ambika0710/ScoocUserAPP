package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.net.invokers.CheckPasswordInvoker;
import com.scooc.scooc.net.invokers.NewPasswordInvoker;

import org.json.JSONObject;

public class CheckPasswordTask extends AsyncTask<String, Integer, BasicBean> {

    private CheckPasswordTaskListener checkPasswordTaskListener;

    private JSONObject postData;

    public CheckPasswordTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        CheckPasswordInvoker checkPasswordInvoker = new CheckPasswordInvoker(null, postData);
        return checkPasswordInvoker.invokeNewPasswordWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            checkPasswordTaskListener.dataDownloadedSuccessfully(result);
        else
            checkPasswordTaskListener.dataDownloadFailed();
    }

    public static interface CheckPasswordTaskListener {

        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public CheckPasswordTaskListener getNewPasswordTaskListener() {
        return checkPasswordTaskListener;
    }

    public void setNewPasswordTaskListener(CheckPasswordTaskListener checkPasswordTaskListener) {
        this.checkPasswordTaskListener = checkPasswordTaskListener;
    }
}
