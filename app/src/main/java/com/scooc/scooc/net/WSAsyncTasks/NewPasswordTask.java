package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import com.scooc.scooc.model.BaseBean;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.net.invokers.NewPasswordInvoker;

public class NewPasswordTask extends AsyncTask<String, Integer, BasicBean> {

    private NewPasswordTaskListener newPasswordTaskListener;

    private JSONObject postData;

    public NewPasswordTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        NewPasswordInvoker newPasswordInvoker = new NewPasswordInvoker(null, postData);
        return newPasswordInvoker.invokeNewPasswordWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            newPasswordTaskListener.dataDownloadedSuccessfully(result);
        else
            newPasswordTaskListener.dataDownloadFailed();
    }

    public static interface NewPasswordTaskListener {

        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public NewPasswordTaskListener getNewPasswordTaskListener() {
        return newPasswordTaskListener;
    }

    public void setNewPasswordTaskListener(NewPasswordTaskListener newPasswordTaskListener) {
        this.newPasswordTaskListener = newPasswordTaskListener;
    }
}
