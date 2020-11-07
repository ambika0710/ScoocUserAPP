package com.scooc.scooc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.scooc.scooc.R;
import com.scooc.scooc.app.App;
import com.scooc.scooc.listeners.BasicListener;
import com.scooc.scooc.listeners.LoginListener;
import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.model.CountryBean;
import com.scooc.scooc.model.CountryListBean;
import com.scooc.scooc.model.RegistrationBean;
import com.scooc.scooc.net.DataManager;
import com.scooc.scooc.util.AppConstants;
import com.scooc.scooc.widgets.OTPEditText;




public class OTP_ForgotPassword extends BaseAppCompatNoDrawerActivity {
    private Spinner spinner1;

    private static final String TAG = "SignUpActivity";

    /*    private DigitsAuthButton digitsButton;
        private AuthCallback digitsCallback;
        private AuthConfig authConfig;*/
    private String phone;
    private RegistrationBean registrationBean;
    private ViewFlipper viewFlipper;
    private EditText etxtPhone;
    private EditText etxtconfirmPassword;

    private EditText etxtPassword;
    private LinearLayout llVerification;
    private TextView txtVerificationLabel;
    private Spinner spinnerCountryCodes;
    private CountryListBean countryListBean;
    private ArrayAdapter<String> adapterCountryCodes;
    private ImageView ivFlag;
    private OTPEditText etxtOne;
    private OTPEditText etxtTwo;
    private OTPEditText etxtThree;
    private OTPEditText etxtFour;
    private OTPEditText etxtFive;
    private OTPEditText etxtSix;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean isVerificationEnabled;
    private String otpCode="438056";

    String userphonenumber;
    RadioGroup rg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p__forgot_password);

      /*  if (Male.isChecked()) {
            selectedGender = Male.getText().toString();
        } else if (Female.isChecked()) {
            selectedGender = Female.getText().toString();
        } else if (NotDisclose.isChecked()) {
            selectedGender = NotDisclose.getText().toString();
        }*/

        initViews();
      /*  addListenerOnSpinnerItemSelection();
        addListenerOnButton();
*/
        if (getIntent().hasExtra("phone_number_user")) {
            phone = getIntent().getStringExtra("phone_number_user");
            etxtPhone.setText(phone);
            registrationBean.setPhone("+91"+phone);

        }

        getSupportActionBar().setTitle(R.string.title_register);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        // spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                lytContent.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);

                onHomeClick();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void initViews() {

        registrationBean = new RegistrationBean();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_registration);
        viewFlipper.setDisplayedChild(0);

        llVerification = (LinearLayout) findViewById(R.id.ll_registration_mobile_otp);
        txtVerificationLabel = (TextView) findViewById(R.id.txt_registration_mobile_otp_label);

        spinnerCountryCodes = (Spinner) findViewById(R.id.spinner_registration_mobile_country_code);
        countryListBean = AppConstants.getCountryBean();
        Collections.sort(countryListBean.getCountries());
        List<String> countryDialCodes = new ArrayList<>();
        for (CountryBean bean : countryListBean.getCountries()) {
            countryDialCodes.add(bean.getDialCode());
        }

//        adapterCountryCodes = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);
        adapterCountryCodes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryDialCodes);
        adapterCountryCodes.setDropDownViewResource(R.layout.item_spinner);
        spinnerCountryCodes.setAdapter(adapterCountryCodes);

        ivFlag = (ImageView) findViewById(R.id.iv_registration_mobile_country_flag);



        etxtPhone = (EditText) findViewById(R.id.etxt_registration_phone);
        etxtconfirmPassword = (EditText) findViewById(R.id.etxt_confirmation_password);
        etxtPassword = (EditText) findViewById(R.id.etxt_registration_password);

        etxtOne = (OTPEditText) findViewById(R.id.etxt_registration_mobile_one);
        etxtTwo = (OTPEditText) findViewById(R.id.etxt_registration_mobile_two);
        etxtThree = (OTPEditText) findViewById(R.id.etxt_registration_mobile_three);
        etxtFour = (OTPEditText) findViewById(R.id.etxt_registration_mobile_four);
        etxtFive = (OTPEditText) findViewById(R.id.etxt_registration_mobile_five);
        etxtSix = (OTPEditText) findViewById(R.id.etxt_registration_mobile_six);


        etxtOne.setTypeface(typeface);
        etxtTwo.setTypeface(typeface);
        etxtThree.setTypeface(typeface);
        etxtFour.setTypeface(typeface);
        etxtFive.setTypeface(typeface);
        etxtSix.setTypeface(typeface);


        etxtPhone.setTypeface(typeface);

        etxtPassword.setTypeface(typeface);
        etxtPassword.setTransformationMethod(new PasswordTransformationMethod());

        viewFlipper.setDisplayedChild(0);
        etxtPhone.setText(phone);
        mAuth = FirebaseAuth.getInstance();
        setVerificationLayoutVisibility(false);

        Glide.with(getApplicationContext())
                .load("file:///android_asset/" + "flags/"
                        + "in" + ".gif")
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop())
                .into(ivFlag);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        spinnerCountryCodes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(getApplicationContext())
                        .load("file:///android_asset/" + "flags/"
                                + "in" + ".gif")
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop())
                        .into(ivFlag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Glide.with(getApplicationContext())
                        .load("file:///android_asset/" + "flags/"
                                + "in"+ ".gif")
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop())
                        .into(ivFlag);
            }
        });

        // Add date of birth

/*
        registrationBean.setReferralcode("+910000000000");
*/

        // edited dob complete

        etxtOne.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etxtOne.getText().length();

                if (textlength1 >= 1) {
                    etxtOne.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtTwo.requestFocus();
                } else {
                    etxtOne.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        etxtTwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = etxtTwo.getText().length();

                if (textlength2 >= 1) {
                    etxtTwo.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtThree.requestFocus();
                } else {
                    etxtTwo.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        etxtThree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength3 = etxtThree.getText().length();

                if (textlength3 >= 1) {
                    etxtThree.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtFour.requestFocus();
                } else {
                    etxtThree.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        etxtFour.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFour.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtFive.requestFocus();
                } else {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        etxtFive.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFive.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFive.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtSix.requestFocus();
                } else {
                    etxtFive.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        etxtSix.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtSix.getText().toString().length();

                if (textlength4 == 1) {
                    etxtSix.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                } else {
                    etxtSix.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        etxtSix.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtSix.getText().toString().length();
                if (i == 0) {
                    etxtFive.setText("");
                    etxtFive.requestFocus();
                }
            }
        });
        etxtFive.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtFive.getText().toString().length();
                if (i == 0) {
                    etxtFour.setText("");
                    etxtFour.requestFocus();
                }
            }
        });
        etxtFour.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtFour.getText().toString().length();
                if (i == 0) {
                    etxtThree.setText("");
                    etxtThree.requestFocus();
                }
            }
        });

        etxtThree.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtThree.getText().toString().length();
                if (i == 0) {
                    etxtTwo.setText("");
                    etxtTwo.requestFocus();
                }
            }
        });

        etxtTwo.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtTwo.getText().toString().length();
                if (i == 0) {
                    etxtOne.setText("");
                    etxtOne.requestFocus();
                }
            }
        });

        etxtSix.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFour.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFive.requestFocus();
                    }
                }
            }
        });

        etxtFive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFour.requestFocus();
                    }
                }
            }
        });

        etxtFour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    }
                }
            }
        });

        etxtThree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    }

                }
            }
        });

        etxtTwo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    }
                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.i(TAG, "onVerificationFailed: " + e);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.i(TAG, "onVerificationFailed: " + e);
                }

                /*Snackbar.make(coordinatorLayout, R.string.message_phone_verification_failed, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
*/
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification id.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification id and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                Snackbar.make(coordinatorLayout, getString(R.string.message_verification_code_sent_to) + " " + registrationBean.getPhone(),
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                setVerificationLayoutVisibility(true);
                swipeView.setRefreshing(false);

            }
        };

    }

    private void setVerificationLayoutVisibility(boolean isVisible) {

        if (isVisible) {
            llVerification.setVisibility(View.VISIBLE);
            txtVerificationLabel.setVisibility(View.VISIBLE);
            etxtOne.requestFocus();
            isVerificationEnabled = true;
        } else {
            llVerification.setVisibility(View.GONE);
            txtVerificationLabel.setVisibility(View.GONE);
            etxtOne.setText("");
            etxtTwo.setText("");
            etxtThree.setText("");
            etxtFour.setText("");
            etxtFive.setText("");
            etxtSix.setText("");
            isVerificationEnabled = false;
        }

    }

    /* MOBILE NUMBER REGISTRATION */

    public void onRegistrationMobileNumberSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);En

//        viewFlipper.setDisplayedChild(0);

        if (collectMobileNumber()) {
            // performPhoneRegistration();
            performMobileAvailabilityCheck(registrationBean.getPhone());
        }

        if (true) {
            otpCode = "" + etxtOne.getText().toString() + etxtTwo.getText().toString()
                    + etxtThree.getText().toString() + etxtFour.getText().toString()
                    + etxtFive.getText().toString() + etxtSix.getText().toString();

            if (!otpCode.equalsIgnoreCase("")) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpCode);
                signInWithPhoneAuthCredential(credential);
            } else {
                Snackbar.make(coordinatorLayout, getString(R.string.message_invalid_verification_code), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
            }

        } else {
            if (collectMobileNumber()) {
//            performPhoneRegistration();
                performMobileAvailabilityCheck(registrationBean.getPhone());
            }
        }
    }

    private boolean collectMobileNumber() {

        Log.i(TAG, "collectMobileNumber: Spinner Value : " + spinnerCountryCodes.getSelectedItem().toString());

        if (spinnerCountryCodes.getSelectedItem().toString().equalsIgnoreCase("")) {
            Snackbar.make(coordinatorLayout, getString(R.string.message_please_select_a_country_dial_code), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
            return false;
        }
    /*    if (etxtPhone.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(coordinatorLayout, getString(R.string.message_phone_number_is_required), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
            return false;
        }
*/
      //  etxtPhone.setText(phone);
        registrationBean.setPhone("+91"+phone);

        return true;
    }

    private void initiatePhoneVerification() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                registrationBean.getPhone(),        // Phone number to verify
                2,                 // Timeout duration
                TimeUnit.MINUTES,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);


        Snackbar.make(coordinatorLayout, R.string.message_sending_verification_code, Snackbar.LENGTH_LONG)
                .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        swipeView.setRefreshing(true);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        swipeView.setRefreshing(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            swipeView.setRefreshing(false);
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            Log.i(TAG, "onComplete: " + new Gson().toJson(task));

                            viewFlipper.setInAnimation(slideLeftIn);
                            viewFlipper.setOutAnimation(slideLeftOut);
                            viewFlipper.showNext();
                            getSupportActionBar().show();
                            swipeView.setPadding(0, (int) mActionBarHeight, 0, 0);
                            swipeView.setPadding(0, (int) mActionBarHeight, 0, 0);


                        } else {
                            swipeView.setRefreshing(false);
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                Snackbar.make(coordinatorLayout, R.string.message_invalid_verification_code, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                            }
                        }
                    }
                });
    }


    public void performMobileAvailabilityCheck(final String phone) {

//        setProgressScreenVisibility(true, true);

        swipeView.setRefreshing(true);

        JSONObject postData = getPhoneNumberAvailabilityJSObj(phone);
        swipeView.setRefreshing(false);

        initiatePhoneVerification();
/*
        DataManager.performMobileAvailabilityCheckErrorOTP(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
//                setProgressScreenVisibility(false, false);

                if (basicBean.isPhoneAvailable()) {
                    initiatePhoneVerification();
                } else {
                    // added by me
                    */
/*  initiatePhoneVerification();*//*

                    Snackbar.make(coordinatorLayout, phone + " " + getString(R.string.message_is_already_registered), Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
//                setProgressScreenVisibility(false, false);
            }
        });
*/
    }

    private JSONObject getPhoneNumberAvailabilityJSObj(String phone) {

        JSONObject postData = new JSONObject();
        try {
            postData.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }





    public void onRegistrationPasswordSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        if (collectPassword()) {
           /* viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();*/

            if (App.isNetworkAvailable()) {
                performUpdatePassword();
            } else {
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }

    }

    private boolean collectPassword() {

        registrationBean.setPassword(etxtPassword.getText().toString());

        if (registrationBean.getPassword() == null || registrationBean.getPassword().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_password_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (registrationBean.getPassword().length() < 8) {
            Snackbar.make(coordinatorLayout, R.string.message_password_minimum_character, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }







    public void onRegistrationNameSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        //mVibrator.vibrate(25);
        if (collectPassword() ) {

            // 31/12/2019
           /* viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();*/
            if (App.isNetworkAvailable()) {
                performUpdatePassword();
            } else {
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }
    }
/*
    private void performChangePassword() {

        swipeView.setRefreshing(true);

        JSONObject postData = getRegistrationJSObj();

        DataManager.performChangePassword(postData, new UpdatePasswordListener() {

            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);

                App.saveToken(authBean);

                startActivity(new Intent(OTP_ForgotPassword.this, LandingPageActivity.class));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }
*/

  /*  private void performRegistration() {

        swipeView.setRefreshing(true);

        JSONObject postData = getRegistrationJSObj();

        DataManager.performRegistration(postData, new RegistrationListener() {

            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);

                App.saveToken(authBean);

                startActivity(new Intent(OTP_ForgotPassword.this, LandingPageActivity.class));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }*/

/*
    private JSONObject getRegistrationJSObj() {

        JSONObject postData = new JSONObject();

        try {

            Log.i(TAG, "getRegistrationJSObj: Mobile" + registrationBean.getPhone());

            postData.put("phone_number", registrationBean.getPhone());

            postData.put("password", registrationBean.getPassword());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
*/




    public void performUpdatePassword() {


        swipeView.setRefreshing(true);
        JSONObject postData = getLoginJSObj();

        DataManager.performForgotPasswordChange(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);


                Toast.makeText(getApplicationContext(), "Password has been updated",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(OTP_ForgotPassword.this, LoginActivity.class));
                finish();

            }

            @Override
            public void onLoadFailed(String error) {

                Snackbar.make(coordinatorLayout, "Sorry! Please Try Again", Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getLoginJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("phone_number", registrationBean.getPhone());

            postData.put("password", registrationBean.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //            onBackPressed();

            onHomeClick();
            return true;
        }
        return false;
    }


    private void onHomeClick() {
        int index = viewFlipper.getDisplayedChild();
        if (index > 1) {
            viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
        } else if (index == 1) {
            viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
            setVerificationLayoutVisibility(false);
            getSupportActionBar().hide();
            swipeView.setPadding(0, 0, 0, 0);
        } else {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
    }
}



