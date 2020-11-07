package com.scooc.scooc.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import com.scooc.scooc.model.PromoCodeBean;
import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.invokers.PromoCodeInvoker;
import com.scooc.scooc.net.invokers.UserInfoInvoker;

public class PromoCodeTask extends AsyncTask<String, Integer, PromoCodeBean> {

    private PromoCodeTaskListener promoCodeTaskListener;

    private HashMap<String, String> urlParams;

    public PromoCodeTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected PromoCodeBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        PromoCodeInvoker promoCodeInvoker = new PromoCodeInvoker(urlParams, null);
        return promoCodeInvoker.invokePromoCodeWS();
    }

    @Override
    protected void onPostExecute(PromoCodeBean result) {
        if (result != null)
            promoCodeTaskListener.dataDownloadedSuccessfully(result);
        else
            promoCodeTaskListener.dataDownloadFailed();
    }

    public interface PromoCodeTaskListener {

        void dataDownloadedSuccessfully(PromoCodeBean promoCodeBean);

        void dataDownloadFailed();
    }

    public PromoCodeTaskListener getPromoCodeTaskListener() {
        return promoCodeTaskListener;
    }

    public void setPromoCodeTaskListener(PromoCodeTaskListener promoCodeTaskListener) {
        this.promoCodeTaskListener = promoCodeTaskListener;
    }
}
