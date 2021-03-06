package com.scooc.scooc.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.snackbar.Snackbar;
import com.scooc.scooc.R;
import com.scooc.scooc.listeners.BasicListener;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.net.DataManager;



public class ForgotPasswordActivity extends BaseAppCompatNoDrawerActivity {

    private EditText etxtEmail;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        initViews();

    }

    private void initViews() {

        etxtEmail = (EditText) findViewById(R.id.etxt_email_forgot_password);
    }

    private boolean validateEmail() {
        closeKeyBoard();
        email = etxtEmail.getText().toString();

        if (email.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }


    public void performNewPassword() {
        closeKeyBoard();
        swipeView.setRefreshing(true);
        JSONObject postData = getNewPasswordJSObj();

        DataManager.performNewPassword(postData, new BasicListener(){

            @Override
            public void onLoadCompleted(BasicBean basicBean) {

                swipeView.setRefreshing(false);
                Toast.makeText(getApplicationContext(), R.string.message_your_new_password_is_sent_to_your_email_address,
                        Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                finish();

            }
        });
    }

    private JSONObject getNewPasswordJSObj() {

        JSONObject postData = new JSONObject();

        try {

            postData.put("email", etxtEmail.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onSubmitButtonClick(View view) {
        closeKeyBoard();
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (validateEmail())
            performNewPassword();

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

