package com.scooc.scooc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.snackbar.Snackbar;
import com.scooc.scooc.R;
import com.scooc.scooc.app.App;
import com.scooc.scooc.listeners.LoginListener;
import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.net.DataManager;
import com.scooc.scooc.util.AppConstants;

public class LoginActivity extends BaseAppCompatNoDrawerActivity {

    private EditText etxtUserName;
    private EditText etxtPassword;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private String TAG = " ";



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }

     // getSupportActionBar();
        getSupportActionBar().setTitle("");
      //  swipeView.setPadding(0, 0, 0, 0);
//        lytBase.setFitsSystemWindows(false);

        initViews();
    }

    public void initViews() {

        etxtUserName = (EditText) findViewById(R.id.etxt_login_email);
        etxtPassword = (EditText) findViewById(R.id.etxt_login_password);
        etxtPassword.setTypeface(typeface);
        etxtUserName.setTypeface(typeface);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            onBackPressed();
        }
        return true;
    }

    public void onLoginButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        closeKeyBoard();

        if (collectLoginData()) {
            if (App.isNetworkAvailable()) {
                if(validate())
                {
                    performLogin();
                }

            } else {
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }

       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/
    }

    private boolean collectLoginData() {
        closeKeyBoard();

        if (etxtUserName.getText().toString().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_email_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        //else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etxtUserName.getText().toString()).matches()) {
        //    Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
         //           .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
       //     return false;
     //   }
        if (etxtPassword.getText().toString().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_password_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }
    private boolean validate() {

        String MobilePattern = "[0-9]{10}";
        //String email1 = email.getText().toString().trim();




        if (etxtUserName.getText().length() == 0 ) {

            Toast.makeText(getApplicationContext(), "pls fill the empty fields", Toast.LENGTH_SHORT).show();
            return false;

        }  else if(etxtUserName.getText().toString().matches(MobilePattern)) {
            return true;

        } else if(!etxtUserName.getText().toString().matches(MobilePattern)) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return false;
    }


    public void performLogin() {
        closeKeyBoard();

        swipeView.setRefreshing(true);
        JSONObject postData = getLoginJSObj();

        DataManager.performLogin(postData, new LoginListener() {
            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);
                authBean.setPhoneVerified(true);
                App.saveToken(authBean);

                Log.i(TAG, "onLoadCompleted: UserId " + authBean.getUserID());

                Toast.makeText(getApplicationContext(), R.string.message_login_is_successful,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
                finish();

            }

            @Override
            public void onLoadFailed(String error) {

                Snackbar.make(coordinatorLayout, R.string.message_login_failed, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getLoginJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("username", "+91" + etxtUserName.getText().toString());  // update
            postData.put("password", etxtPassword.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onForgotPasswordClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordOTPActivity.class);
        startActivity(intent);
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

