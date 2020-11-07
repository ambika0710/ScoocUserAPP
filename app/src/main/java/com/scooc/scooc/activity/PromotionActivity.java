package com.scooc.scooc.activity;

import android.content.Context;
import android.os.Bundle;

import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.snackbar.Snackbar;
import com.scooc.scooc.R;
import com.scooc.scooc.listeners.FreeRideListener;
import com.scooc.scooc.model.FreeRideBean;
import com.scooc.scooc.model.TripBean;
import com.scooc.scooc.net.DataManager;

public class PromotionActivity extends BaseAppCompatNoDrawerActivity {

    private Toolbar toolbarPromotion;
    private TextView txtDateTime;
    private TripBean bean;
    private EditText etxtPromoCode;
    private TextView txtAddCode;
    private String freeRideCode;
    private View.OnClickListener snackBarRefreshOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (etxtPromoCode.isShown()) {
                onBackClick();

            } else {
                onBackPressed();
            }
        }
        return true;
    }

    private void onBackClick() {

       /* etxtPromoCode.setVisibility(View.GONE);
        txtAddCode.setText("ADD CODE");*/
        finish();
    }

    public void initViews() {

        coordinatorLayout.removeView(toolbar);

        toolbarPromotion = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_promotion, toolbar);
        coordinatorLayout.addView(toolbarPromotion, 0);
        setSupportActionBar(toolbarPromotion);

        etxtPromoCode = (EditText) findViewById(R.id.etxt_promo_code);
        txtAddCode = (TextView) findViewById(R.id.txt_add_code);

    }

    public boolean validatePromocode() {
        closeKeyBoard();
        boolean flag = true;

        if (etxtPromoCode.getText().toString().length() <= 2) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_promocode, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.btn_refresh, snackBarRefreshOnClickListener).show();
            etxtPromoCode.requestFocus();
            flag = false;
        }
        return flag;
    }

    public void performFreeRide() {
        closeKeyBoard();
        swipeView.setRefreshing(true);
        JSONObject postData = getFreeRideJSObj();

        DataManager.performFreeRide(postData, new FreeRideListener() {

            @Override
            public void onLoadCompleted(FreeRideBean freeRideBean) {

                swipeView.setRefreshing(false);

                Toast.makeText(getApplicationContext(), R.string.message_promocode_is_successfully_sent, Toast.LENGTH_LONG).show();

             //   finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

            //    finish();
            }
        });
    }

    private JSONObject getFreeRideJSObj() {
        JSONObject postData = new JSONObject();

        freeRideCode = etxtPromoCode.getText().toString();

        try {
            postData.put("free_ride_code", freeRideCode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onAddCodeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (etxtPromoCode.isShown()) {
            if (validatePromocode()) {
                performFreeRide();
            }
        } else {
            etxtPromoCode.setVisibility(View.VISIBLE);
            txtAddCode.setText(R.string.btn_apply_promo_code);
        }
    }

    public void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
