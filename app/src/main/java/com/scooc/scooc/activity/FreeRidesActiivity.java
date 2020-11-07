package com.scooc.scooc.activity;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import com.scooc.scooc.R;
import com.scooc.scooc.config.Config;
import com.scooc.scooc.listeners.PromoCodeListener;
import com.scooc.scooc.listeners.UserInfoListener;

import com.scooc.scooc.model.PromoCodeBean;
import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.DataManager;

import com.scooc.scooc.model.PromoCodeBean;

public class FreeRidesActiivity extends BaseAppCompatNoDrawerActivity {

    private Toolbar toolbarFreeRides;
    private PromoCodeBean promoCodeBean;
    private TextView txtPromoCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_rides);

        initViews();

//        swipeView.setRefreshing(true);
        setProgressScreenVisibility(true, true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    public void initViews() {

        coordinatorLayout.removeView(toolbar);

        toolbarFreeRides = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_free_rides, toolbar);
        coordinatorLayout.addView(toolbarFreeRides, 0);
        setSupportActionBar(toolbarFreeRides);

        txtPromoCode = (TextView) findViewById(R.id.txt_promo_code);

//        fetchPromoCode();
    }

    public void fetchPromoCode() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchPromoCode(urlParams, new PromoCodeListener() {

            @Override
            public void onLoadCompleted(PromoCodeBean promoCodeBean) {

                swipeView.setRefreshing(false);
                setProgressScreenVisibility(false, false);

                System.out.println("Successfull  : UserBean : " + promoCodeBean);
                FreeRidesActiivity.this.promoCodeBean = promoCodeBean;
                populatePromoCode(promoCodeBean);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
               /* Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();*/

            }
        });
    }

    private void populatePromoCode(PromoCodeBean promoCodeBean) {

        txtPromoCode.setText(promoCodeBean.getPromoCode());
    }
}
