package com.scooc.scooc.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.R;
import com.scooc.scooc.app.App;
import com.scooc.scooc.config.Config;
import com.scooc.scooc.dialogs.PopupMessage;
import com.scooc.scooc.listeners.BasicListener;
import com.scooc.scooc.listeners.DriverDetailsListener;
import com.scooc.scooc.listeners.RequestRideListener;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.model.DriverBean;
import com.scooc.scooc.model.FareBean;
import com.scooc.scooc.model.PlaceBean;
import com.scooc.scooc.model.RequestBean;
import com.scooc.scooc.net.DataManager;

;

public class RequestingPageActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "RPA";
    //    private String source;
//    private String destination;
    private TextView txtDummy;
    private String carType;
    //    private double sourceLatitude;
//    private double sourceLongitude;
    private Handler mHandler = new Handler();
    //    private double destinationLatitude;
//    private double destination_longitude;
    private RequestBean requestBean;
    private String id;
    private String pickup_time,start_date,end_date;
    private DriverBean driverBean;
    private FareBean fareBean;
    private TextView txtRequestingPageTotalFare;
    private String tripID;
    private String requestStatus;
    private boolean isRequestHandled = false;
    private PlaceBean sourceBean;
    private PlaceBean destinationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requesting_page);


        fareBean = (FareBean) getIntent().getSerializableExtra("fare_bean");
        sourceBean = (PlaceBean) getIntent().getSerializableExtra("source_bean");
        destinationBean = (PlaceBean) getIntent().getSerializableExtra("destination_bean");
        carType = getIntent().getStringExtra("car_type");
      /*  pickup_time = getIntent().getStringExtra("pickup_time"); //add25/03/2020 new feature
        start_date = getIntent().getStringExtra("start_date");
        end_date = getIntent().getStringExtra("end_date");*/

        pickup_time = LandingPageActivity.pickup_time;
        start_date =LandingPageActivity.start_date;
        end_date = LandingPageActivity.end_date;

/*        sourceLatitude = Double.parseDouble(getIntent().getStringExtra("source_latitude"));
        sourceLongitude = Double.parseDouble(getIntent().getStringExtra("source_longitude"));
        destinationLatitude = Double.parseDouble(getIntent().getStringExtra("destination_latitude"));
        destination_longitude = Double.parseDouble(getIntent().getStringExtra("destination_longitude"));*/

        Log.i(TAG, "onCreate: FareBean" + new Gson().toJson(fareBean));
        Log.i(TAG, "onCreate: CarType" + carType);
     /*   Log.i(TAG, "onCreate: pickup_time" + pickup_time);
        Log.i(TAG, "onCreate: start_date" + start_date);
        Log.i(TAG, "onCreate: end_date" + end_date);
*/
        Log.i(TAG, "onCreate: SourceBean" + new Gson().toJson(sourceBean));
        Log.i(TAG, "onCreate: DestinationBean" + new Gson().toJson(destinationBean));

        initViews();

        swipeView.setRefreshing(true);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        performRequestRide();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (App.isNetworkAvailable()) {
                performRequestCancel();
            }
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }


    @Override
    protected void onDestroy() {

        if (!isRequestHandled)
            performRequestCancel();

        super.onDestroy();
    }

    private void initViews() {

//        Log.i(TAG, "initViews: Total Fare" + fareBean.getTotalFare());

        txtRequestingPageTotalFare = (TextView) findViewById(R.id.txt_requesting_page_total_fare);

        txtRequestingPageTotalFare.setText(fareBean.getTotalFare());
    }

    public void onCancelClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        performRequestCancel();
        isRequestHandled = true;
        mHandler.removeCallbacks(requestTask);
        finish();
    }

    Runnable requestTask = new Runnable() {
        @Override
        public void run() {

            if (!isRequestHandled) {
//                performRequestTriggering();
                fetchRequestStatus();
                mHandler.postDelayed(requestTask, 5000);
            }
        }
    };

    Runnable triggerTask = new Runnable() {
        @Override
        public void run() {

            if (!isRequestHandled) {
                performRequestTriggering();
                mHandler.postDelayed(triggerTask, 65000);
            }
        }
    };

    public void performRequestRide() {

        Log.i(TAG, "performRequestRide: AuthToken" + Config.getInstance().getAuthToken());

        swipeView.setRefreshing(true);
        JSONObject postData = getRequestRideJSObj();

        DataManager.performRequestRide(postData, new RequestRideListener() {

            @Override
            public void onLoadCompleted(RequestBean requestBeanWS) {

                requestBean = requestBeanWS;
                swipeView.setRefreshing(false);
                mHandler.post(triggerTask);
                mHandler.post(requestTask);
                fetchRequestStatus();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                finish();

            }
        });
    }

    private JSONObject getRequestRideJSObj() {

        JSONObject postData = new JSONObject();
/*
        try {
            postData.put("source", sourceBean.getName());
            postData.put("destination", destinationBean.getName());
            postData.put("car_type", carType);
            postData.put("source_latitude", sourceBean.getLatitude());
            postData.put("source_longitude", sourceBean.getLongitude());
            postData.put("destination_latitude", destinationBean.getLatitude());
            postData.put("destination_longitude", destinationBean.getLongitude());*/



            try {
                postData.put("source", sourceBean.getName());
                postData.put("destination", destinationBean.getName());
                postData.put("car_type", carType);
                postData.put("source_latitude", sourceBean.getLatitude());
                postData.put("source_longitude", sourceBean.getLongitude());
                postData.put("destination_latitude", destinationBean.getLatitude());
                postData.put("destination_longitude", destinationBean.getLongitude());
                postData.put("pickup_time", "11:00AM");
                postData.put("start_date", "2020-03-26");
                postData.put("end_date", "2020-03-27");


            } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void performRequestTriggering() {

        swipeView.setRefreshing(true);
        JSONObject postData = getRequestTriggeringJSObj();

        DataManager.performRequestTriggering(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {

            }

            @Override
            public void onLoadFailed(String error) {
//                swipeView.setRefreshing(false);

                Toast.makeText(RequestingPageActivity.this, R.string.message_no_driver_available, Toast.LENGTH_SHORT).show();
                if (App.isNetworkAvailable()) {
                    performRequestCancel();
                }
                isRequestHandled = true;
                mHandler.removeCallbacks(requestTask);
                finish();

            }
        });
    }

    private JSONObject getRequestTriggeringJSObj() {

        JSONObject postData = new JSONObject();

        try {
            postData.put("id", requestBean.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void fetchRequestStatus() {

        swipeView.setRefreshing(true);

        id = requestBean.getId();

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("id", id);

        DataManager.fetchRequestStatus(urlParams, new DriverDetailsListener() {

            @Override
            public void onLoadCompleted(DriverBean driverBeanWS) {

//                swipeView.setRefreshing(false);
                Log.i(TAG, "onLoadCompleted: DriverBean : " + driverBeanWS);

                driverBean = driverBeanWS;

                if (driverBean.getRequestStatus().equalsIgnoreCase("1")) {
                    isRequestHandled = true;
                    mHandler.removeCallbacks(requestTask);
                    Intent intent = new Intent();
                    intent.putExtra("fareBean", driverBeanWS);
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (driverBean.getRequestStatus().equalsIgnoreCase("2")) {
                    isRequestHandled = true;
                    mHandler.removeCallbacks(requestTask);
                    Intent intent = new Intent();
//                    intent.putExtra("fareBean", driverBeanWS);
                    setResult(RESULT_CANCELED, intent);
                    finish();

                }
            }

            @Override
            public void onLoadFailed(String error) {
//                swipeView.setRefreshing(false);

                if (App.isNetworkAvailable()) {
                    performRequestCancel();
                }
                isRequestHandled = true;
                mHandler.removeCallbacks(requestTask);
                finish();
            }
        });
    }

    public void performRequestCancel() {

        swipeView.setRefreshing(true);
        JSONObject postData = getRequestCancelJSObj();

        DataManager.performRequestCancel(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!isDestroyed() && !isFinishing()) {
                        showErrorPopup(error);
                    }
                } else {
                    if (!isFinishing()) {
                        showErrorPopup(error);
                    }
                }

            }
        });
    }

    private void showErrorPopup(String error) {
        PopupMessage popupMessage = new PopupMessage(RequestingPageActivity.this);
        popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
            @Override
            public void actionCompletedSuccessfully(boolean result) {
                if (App.isNetworkAvailable()) {
                    performRequestCancel();
                }
            }

            @Override
            public void actionFailed() {

            }
        });
        popupMessage.show(error, 0, getString(R.string.btn_retry));
    }

    private JSONObject getRequestCancelJSObj() {

        JSONObject postData = new JSONObject();

        try {
            if (requestBean != null) {
                postData.put("request_id", requestBean.getId());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}