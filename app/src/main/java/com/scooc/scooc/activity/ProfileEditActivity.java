package com.scooc.scooc.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.text.InputType;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.scooc.scooc.R;
import com.scooc.scooc.app.App;
import com.scooc.scooc.config.Config;
import com.scooc.scooc.dialogs.SelectPhotoDialog;
import com.scooc.scooc.listeners.BasicListener;
import com.scooc.scooc.listeners.EditProfileListener;
import com.scooc.scooc.listeners.UserInfoListener;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.DataManager;
import com.scooc.scooc.util.AppConstants;
import com.scooc.scooc.util.ImageFilePath;

public class ProfileEditActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "ProfileEditA";
    private static final int REQUEST_IMAGE_CAMERA = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int REQ_MOBILE_VERIFICATION = 3;
    private Toolbar toolbarEdit;
    private EditText etxtName;
    private EditText etxtEmail;
    private TextView etxtgender;

    //   private TextView etxtPhone;
    //  private TextView etxtdob;
    private DatePickerDialog picker;

    private UserBean userBean;
    private Spinner spinner;
    private ImageView ivProfilePhoto;
    private String imagePath = "";
    private String displayPicImage = "";
    private String email;
    private String gender;
    //   private String dob;
    private ArrayAdapter<CharSequence> adapter;
    private String name;
    private String phone;
    private  ImageView edit_calendar;
    private  ImageView gender_edit;
    AlertDialog alertDialog1;
    CharSequence[] values = {"Male "," Female ","NotDisclose "};
    //    private AuthConfig authConfig;
    private View.OnClickListener snackBarRefreshOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        userBean = (UserBean) getIntent().getSerializableExtra("bean");
        //   edit_calendar = findViewById(R.id.calendar_edit);
        gender_edit = findViewById(R.id.gender_edit);

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        gender_edit.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               CreateAlertDialogWithRadioButtonGroup();
                                           }
                                       }
        );
        //  etxtdob.setInputType(InputType.TYPE_NULL);
    /*    edit_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ProfileEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
*//*
                                eTextdob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
*//*
                              //  etxtdob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, year, month, day);
                picker.show();
            }
        });*/
    }

    public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);

        builder.setTitle("Select Your Choice");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        etxtgender.setText("Male");
                        Toast.makeText(ProfileEditActivity.this, "Male Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        etxtgender.setText("Female");

                        Toast.makeText(ProfileEditActivity.this, "Female Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        etxtgender.setText("Not Disclose");

                        Toast.makeText(ProfileEditActivity.this, "Not Disclose Clicked", Toast.LENGTH_LONG).show();
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (userBean != null) {
            getData(true);
        } else {
            setProgressScreenVisibility(true, true);
            getData(false);
        }*/
    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchUserDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_refresh, snackBarRefreshOnClickListener).show();
        }
    }

    public void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };
       /* AuthConfig.Builder builder = new AuthConfig.Builder();

        builder.withAuthCallBack(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {

                performMobileAvailabilityCheck(phoneNumber);
            }

            @Override
            public void failure(DigitsException exception) {

            }
        });

        authConfig = builder.build();*/

        coordinatorLayout.removeView(toolbar);

        toolbarEdit = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_edit_page, toolbar);
        coordinatorLayout.addView(toolbarEdit, 0);
        setSupportActionBar(toolbarEdit);

        etxtName = (EditText) findViewById(R.id.etxt_name_edit_page);
        etxtEmail = (EditText) findViewById(R.id.etxt_email_edit_page);
        etxtgender = (TextView) findViewById(R.id.etxt_gender_edit_page);
        //   etxtPhone = (TextView) findViewById(R.id.etxt_number_edit_page);
        //  etxtdob = (TextView) findViewById(R.id.etxt_dob_edit_page);

        ivProfilePhoto = (ImageView) findViewById(R.id.iv_profile_photo);

        if (userBean != null)
            populateUserDetails();

    }

    public void populateUserDetails() {

        etxtName.setText(userBean.getName());
        etxtEmail.setText(userBean.getEmail());
        etxtgender.setText(userBean.getGender());
        //    etxtPhone.setText(userBean.getMobileNumber());
        // etxtdob.setText(userBean.getDob());

        Glide.with(getApplicationContext())
                .load(userBean.getProfilePhoto())
                .apply(new RequestOptions()
                        .fallback(R.drawable.ic_profile_photo_default)
                        .error(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    public void fetchUserDetails() {
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("auth_token", Config.getInstance().getAuthToken());

        DataManager.fetchUserInfo(urlParams, Config.getInstance().getUserID(), new UserInfoListener() {
            @Override
            public void onLoadCompleted(UserBean userBeanWS) {
                System.out.println("Successfull  : UserBean : " + new Gson().toJson(userBeanWS));
                userBean = userBeanWS;
                populateUserDetails();
            }

            @Override
            public void onLoadFailed(String errorMsg) {

                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

            }
        });
    }

    public void onSuccessButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (App.isNetworkAvailable()) {
            if (collectEditProfileData()) {
                performEditProfile();
            }
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    private boolean collectEditProfileData() {

        email = etxtEmail.getText().toString();
        gender = etxtgender.getText().toString();
        //   dob = etxtdob.getText().toString();

        if (etxtName.getText().toString().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_name_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        if (email.equalsIgnoreCase("")) {
            Snackbar.make(coordinatorLayout, R.string.message_email_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        return true;
    }

    public void performEditProfile() {

        swipeView.setRefreshing(true);
        JSONObject postData = getEditProfileJSObj();

        List<String> fileList = getFileList();

        DataManager.performEditProfile(postData, fileList, new EditProfileListener() {

            @Override
            public void onLoadCompleted(UserBean userBean) {
                swipeView.setRefreshing(false);

                App.saveToken();
                Toast.makeText(ProfileEditActivity.this, R.string.message_profile_updated_successfully, Toast.LENGTH_LONG).show();
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

    private List<String> getFileList() {
        List<String> fileList = new ArrayList<>();

        if (displayPicImage != null && !displayPicImage.equals(""))
            fileList.add(displayPicImage);

        return fileList;
    }

    private JSONObject getEditProfileJSObj() {
        JSONObject postData = new JSONObject();

        name = etxtName.getText().toString();
        email = etxtEmail.getText().toString();
        gender = etxtgender.getText().toString();
        // dob = etxtdob.getText().toString();

        //  phone = etxtPhone.getText().toString();

        try {
            postData.put("name", name);
            postData.put("gender", gender);
            //     postData.put("dob", dob);
            if (!email.equalsIgnoreCase(userBean.getEmail()))
                postData.put("email", email);
           /* if (!phone.equalsIgnoreCase(userBean.getMobileNumber()))
                postData.put("phone", phone);*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;

    }

    public void onPhotoEditClick(final View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        SelectPhotoDialog selectPhotoDialog = new SelectPhotoDialog(this);
        selectPhotoDialog.setSelectPhotoDialogActionListener(new SelectPhotoDialog.SelectPhotoDialogActionListener() {
            @Override
            public void onSelectGalleryClick() {
                onAddProfilePhotoFromGallery();
            }

            @Override
            public void onSelectCameraClick() {
                onAddProfilePhotoFromCamera();
            }
        });
        selectPhotoDialog.show();
    }


    public void onAddProfilePhotoFromGallery() {

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();

        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);

        }
    }

    public void onAddProfilePhotoFromCamera() {

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile(0);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                if (photoFile != null) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);

                }
            }
        }
    }

    private File createImageFile(int op) throws IOException {
        File image = null;

        if (op == 0) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            String imageFileName = "LaTaxi" + timeStamp + "_";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File storageDir = new File(
                        Environment.getExternalStorageDirectory() + "/LaTaxi/Photo/");
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }
                image = new File(storageDir + imageFileName + ".jpg");
            } else {
                image = new File(getFilesDir() + "/" + imageFileName + ".jpg");
            }

            image.createNewFile();
            // Save a file: path for use with ACTION_VIEW intents
            imagePath = image.getAbsolutePath();
        }
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK) {
            displayPicImage = imagePath;
            //    setBannerPic(tempImagePath);
            setDisplayPic(imagePath);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {

            String imageFilePath = ImageFilePath.getPath(getApplicationContext(), data.getData());
            System.out.println(imageFilePath);

            displayPicImage = imageFilePath;
            setDisplayPic(imageFilePath);

        }
        if (requestCode == REQ_MOBILE_VERIFICATION && resultCode == RESULT_OK) {

            String phone = "";
            if (data.hasExtra("phone"))
                phone = data.getStringExtra("phone");

            Toast.makeText(getApplicationContext(), R.string.message_phone_verified_successfully,
                    Toast.LENGTH_LONG).show();
            // etxtPhone.setText(phone);
        }
    }

    private void setDisplayPic(String tempImagePath) {

        Glide.with(getApplicationContext())
                .load(tempImagePath)
                .apply(new RequestOptions()
                        .error(R.drawable.ic_dummy_photo)
                        .fallback(R.drawable.ic_dummy_photo)
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);

    }

    public void onProfileEditPhoneClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

/*        Digits.logout();
        Digits.authenticate(authConfig);
        new Digits.Builder().withTheme(R.style.AppTheme).build();*/

        FirebaseAuth.getInstance().signOut();
        startActivityForResult(new Intent(this, MobileVerificationActivity.class)
                , REQ_MOBILE_VERIFICATION);

    }

    public void performMobileAvailabilityCheck(final String phoneNumber) {

        swipeView.setRefreshing(true);

        JSONObject postData = getMobileAvailabilityCheckJSObj(phoneNumber);

        DataManager.performMobileAvailabilityCheck(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);

                if (basicBean.isPhoneAvailable()) {
                    Toast.makeText(getApplicationContext(), R.string.message_phone_number_successfully_verified,
                            Toast.LENGTH_LONG).show();
                    // etxtPhone.setText(phoneNumber);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.message_phone_number_already_exists,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }

    private JSONObject getMobileAvailabilityCheckJSObj(String phoneNumber) {

        JSONObject postData = new JSONObject();

        try {

            postData.put("phone", phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}
