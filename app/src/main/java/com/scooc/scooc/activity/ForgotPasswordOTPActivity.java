package com.scooc.scooc.activity;
import android.content.Context;
import android.content.Intent;
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
import com.scooc.scooc.R;
import com.scooc.scooc.listeners.BasicListener;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.net.DataManager;


public class ForgotPasswordOTPActivity extends BaseAppCompatNoDrawerActivity {

    private EditText etxtEmail;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_o_t_p);

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


    public void performNewPassword() {
        closeKeyBoard();
        swipeView.setRefreshing(true);
        JSONObject postData = getNewPasswordJSObj();

       // DataManager.performNewPassword(postData, new BasicListener(){
        DataManager.performCheckPassword(postData, new BasicListener(){
            @Override
            public void onLoadCompleted(BasicBean basicBean) {

                swipeView.setRefreshing(false);
                Toast.makeText(getApplicationContext(), R.string.message_your_new_password_is_sent_to_your_email_address,
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPasswordOTPActivity.this, OTP_ForgotPassword.class);
                intent.putExtra("phone_number_user", etxtEmail.getText().toString());
                System.out.println("POSTDATA>>>>>>>" +  etxtEmail.getText().toString());
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

                Toast.makeText(getApplicationContext(), "Your Number is incorrect",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private JSONObject getNewPasswordJSObj() {

        JSONObject postData = new JSONObject();

        try {

            postData.put("phone_number", "+91"+etxtEmail.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onSubmitButtonClick(View view) {
        closeKeyBoard();
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        if(validate()== true)
        {
            performNewPassword();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Your Mobile  Number is not valid",
                    Toast.LENGTH_LONG).show();
        }

     /*   if (validateEmail())*/


    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean validate() {

        String MobilePattern = "[0-9]{10}";
        //String email1 = email.getText().toString().trim();




    if (etxtEmail.getText().length() == 0 ) {

            Toast.makeText(getApplicationContext(), "pls fill the empty fields", Toast.LENGTH_SHORT).show();
            return false;

        }  else if(etxtEmail.getText().toString().matches(MobilePattern)) {


            return true;

        } else if(!etxtEmail.getText().toString().matches(MobilePattern)) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return false;
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

