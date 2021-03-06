package com.scooc.scooc.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.invokers.UserInfoInvoker;

public class UserInfoTask extends AsyncTask<String, Integer, UserBean> {

    private UserInfoTaskListener userInfoTaskListener;

    private HashMap<String, String> urlParams;

    public UserInfoTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected UserBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        UserInfoInvoker userInfoInvoker = new UserInfoInvoker(urlParams, null);
        return userInfoInvoker.invokeUserInfoWS();
    }

    @Override
    protected void onPostExecute(UserBean result) {
        if (result != null)
            userInfoTaskListener.dataDownloadedSuccessfully(result);
        else
            userInfoTaskListener.dataDownloadFailed();
    }

    public interface UserInfoTaskListener {
        void dataDownloadedSuccessfully(UserBean userBean);

        void dataDownloadFailed();
    }

    public UserInfoTaskListener getUserInfoTaskListener() {
        return userInfoTaskListener;
    }

    public void setUserInfoTaskListener(UserInfoTaskListener userInfoTaskListener) {
        this.userInfoTaskListener = userInfoTaskListener;
    }
}
