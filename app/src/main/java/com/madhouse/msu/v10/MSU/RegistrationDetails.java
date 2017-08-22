package com.madhouse.msu.v10.MSU;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.adapter.SpinnerAdapterHelpdesk;
import com.madhouse.msu.v10.applicationUtils.ApplicationConstants;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.fragments.ExistingEcomApp;
import com.madhouse.msu.v10.fragments.PreferenceSelection;
import com.madhouse.msu.v10.proxies.UserBhamashahBean;
import com.madhouse.msu.v10.proxies.UserRegistrationProxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by krishnapratapsingh on 06/08/17.
 */

public class RegistrationDetails extends Activity {

    private CircleImageView profile_image, idNoPick;
    private TextView userDOB;
    private EditText userName, emergencyNo, useremail, userPassword, editTextIDNo, housenum, area, city, pincode, userMobile;
    private Spinner idType, maritalStaus;
    private Button proceedButton;
    private UserPreferences preferences;
    private ProgressDialog dialog;
    private final static String TAG_FRAGMENT = "CHAT_FRAG";

    private String recieveedNumber,name,dob,firstName,lastName,emailId,gender,mobilenumber,emergemcyContact,userID,
            password,idNoText,idTypeText,maritalStausID,maritalStausName,idTypeID, idTypeName, houseNumVal, areaVal, cityVal, pincodeVal,
             completeAddress;

    public final String SENDER_ID = ApplicationConstants.GOOGLE_GCM_SENDERID;

    private View view;
    private String user_type="", message;
    private boolean bool= false;

    //private ArrayList<HashMap<String, String>> vehicleDataList;

    private Bitmap profilepic, idnumber;
    private String resultProfilePic, resultIDPic;
   // RequestParams params = new RequestParams();
    private String encodedStringprofilepic, getEncodedStringidnumber, deviceID;



    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static String TAG = "LaunchActivity";
    //protected String SENDER_ID = "Your_sender_id";
    //  private GoogleCloudMessaging gcm =null;
    private String regid = null;
    private Context context= null;
    private Toolbar toolbar;
    public static UserBhamashahBean userBhamashah;
    private TextView divId, divName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_register);
        preferences = UserPreferences.getInstance(RegistrationDetails.this);
        dialog = new ProgressDialog(RegistrationDetails.this);

        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        TextView ctoolbar_title = (TextView) toolbar.findViewById(R.id.ctoolbar_title);
        ctoolbar_title.setText(R.string.msu_register_heading);




        initUI();
        //makeRequestUserData();
        setListener();
        //return view;
    }


    private void initUI(){

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        idNoPick = (CircleImageView) findViewById(R.id.idNoPick);

        userName = (EditText) findViewById(R.id.userName);
        userDOB = (TextView) findViewById(R.id.userDOB);
        userMobile = (EditText) findViewById(R.id.userMobile);
        emergencyNo = (EditText) findViewById(R.id.emergencyNo);

        housenum = (EditText) findViewById(R.id.housenumber);
        area = (EditText) findViewById(R.id.area);
        city = (EditText) findViewById(R.id.city);
        pincode = (EditText) findViewById(R.id.pincode);

        useremail = (EditText) findViewById(R.id.useremail);
        userPassword = (EditText) findViewById(R.id.userPassword);
        editTextIDNo = (EditText) findViewById(R.id.editTextIDNo);
        proceedButton = (Button) findViewById(R.id.proceesButton);


        idType = (Spinner) findViewById(R.id.idType);
        maritalStaus = (Spinner) findViewById(R.id.marital_status);


        editTextIDNo.addTextChangedListener(new MyTextWatcher(editTextIDNo));



        idType.setAdapter(new SpinnerAdapterHelpdesk(RegistrationDetails.this, R.layout.custom_spinner,  getResources().getStringArray(R.array.id_type_spinner),
                getResources().getStringArray(R.array.id_type_val_spinner)));

        idType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // spinnerProgress = (ProgressBar) view.findViewById(R.id.spinnerProgress);
                //spinnerProgress.setVisibility(View.GONE);
                //if(position!=0) {
                // LoadComplaintCenter taskCompGroup = new LoadComplaintCenter();
                // Call execute
               // TextView govIDVal = (TextView) view.findViewById(R.id.sub_text_seen);
               // TextView govNameDesc = (TextView) view.findViewById(R.id.text_main_seen);

                //divNAME = String.valueOf(divName.getText().toString());
                //meternum = String.valueOf(divId.getText().toString());

                try {
                    divId = (TextView) view.findViewById(R.id.sub_text_seen);
                    divName = (TextView) view.findViewById(R.id.text_main_seen);

                    idTypeName = String.valueOf(divName.getText().toString());
                    idTypeID = String.valueOf(divId.getText().toString());
                }
                catch (Exception er){
                    er.getMessage();
                }

                final InputFilter[] filterArray = new InputFilter[1];

                if(position==0){
                    editTextIDNo.setText("  ");
                    editTextIDNo.setHint("");

                    editTextIDNo.setEnabled(false);
                    idNoPick.setEnabled(false);

                }

                else if(position==1){
                    editTextIDNo.setEnabled(true);
                    idNoPick.setEnabled(true);

                    editTextIDNo.setText("");
                    editTextIDNo.setHint(getString(R.string.bhamashah_text));
                    filterArray[0] = new InputFilter.LengthFilter(15);
                    editTextIDNo.setFilters(filterArray);
                    editTextIDNo.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                else{
                    editTextIDNo.setEnabled(true);
                    idNoPick.setEnabled(true);
                    editTextIDNo.setText("");
                    editTextIDNo.setHint(getString(R.string.adhaar_text));
                    filterArray[0] = new InputFilter.LengthFilter(12);
                    editTextIDNo.setFilters(filterArray);
                    editTextIDNo.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                //taskCompGroup.execute(divID);
               /* }
                else{
                    // CompGrpValue = "0000";
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                idTypeName = divName.getText().toString();
                idTypeID = divId.getText().toString();

            }
        });


        maritalStaus.setAdapter(new SpinnerAdapterHelpdesk(RegistrationDetails.this, R.layout.custom_spinner,  getResources().getStringArray(R.array.marital_status_array),
                getResources().getStringArray(R.array.marital_status_id_array)));


        maritalStaus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView divId = (TextView) view.findViewById(R.id.sub_text_seen);
                TextView divName= (TextView) view.findViewById(R.id.text_main_seen);

                maritalStausName = String.valueOf(divName.getText().toString());
                maritalStausID = String.valueOf(divId.getText().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void setListener(){

        profile_image.setOnClickListener(mClickListener);
        idNoPick.setOnClickListener(mClickListener);
        userDOB.setOnClickListener(mClickListener);
        proceedButton.setOnClickListener(mClickListener);

    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.profile_image:
                    selectImage(ApplicationConstants.PROFILE_PICK);
                    break;
                case R.id.idNoPick:
                    selectImage(ApplicationConstants.ID_NO);
                    break;
                case R.id.userDOB:
                    ApplicationUtil.hideSoftKeyBoard(RegistrationDetails.this);
                    dateSelected();
                    break;
                case R.id.proceesButton:

//                    Fragment fragment = new PreferenceSelection();
//                    FragmentManager fm = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                    fragmentTransaction.add(R.id.fragment_place, fragment);
//                    fragmentTransaction.commit();

                    //  checkValidationonUI();
                   // getDataFromUI();

                    if (checkValidationonUI()){
                        getDataFromUI();
                        sendUserProfileData("registrationDetails");
                    }
                    else {
                        ApplicationUtil.showToast("Please Check you fill all field",RegistrationDetails.this);
                    }

                    break;

            }
        }
    };
    private void dateSelected(){

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(RegistrationDetails.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                selectedmonth = selectedmonth + 1;

                try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date strDate = null;

                    strDate = sdf.parse(selectedday + "/" + selectedmonth + "/" + selectedyear);

                    if (System.currentTimeMillis() > strDate.getTime()) {
                        userDOB.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        userDOB.setError(null);
                    }
                    else{
                        userDOB.setText(" ");
                        userDOB.setError("");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }





    private boolean checkValidationonUI(){
        String msg = "";
        boolean check=false;

        if (idType.getSelectedItemPosition() == 0){
            msg = getString(R.string.text_select_idtype);
            idType.requestFocus();
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (TextUtils.isEmpty(editTextIDNo.getText())) {
            msg = getString(R.string.text_enter_id_no);
            editTextIDNo.setError(msg);
            editTextIDNo.requestFocus();
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (userName.getText().toString().length() < 4 || userName.getText()==null){
            msg = getString(R.string.text_enter_name);
            userName.requestFocus();
            userName.setError(msg);
            check=false;
        }
        else if (userDOB.getText().toString().length() == 0 || userDOB.getText() == null){
            msg = getString(R.string.text_enter_dob);
            userDOB.requestFocus();
            userDOB.setError(msg);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (userMobile.getText().toString().length() < 10 || userMobile.getText().toString()== null){
            msg = getString(R.string.text_enter_mobile);
            userMobile.requestFocus();
            userMobile.setError(msg);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }



        else if (TextUtils.isEmpty(housenum.getText())) {
            msg = "Enter field value";
            housenum.setError(msg);
            housenum.requestFocus();
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (TextUtils.isEmpty(area.getText())) {
            msg = "Enter field value";
            area.setError(msg);
            area.requestFocus();
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }


        else if (TextUtils.isEmpty(city.getText())) {
            msg = "Enter field value";
            city.setError(msg);
            city.requestFocus();
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }


        else if (TextUtils.isEmpty(pincode.getText()) && pincode.getText().toString().length() == 6) {
            msg = "Enter/Correct field value";
            pincode.setError(msg);
            pincode.requestFocus();
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }



        else if (maritalStaus.getSelectedItemPosition() == 0){
            msg = getString(R.string.marital_status);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (TextUtils.isEmpty(useremail.getText().toString())){
            msg = getString(R.string.text_enter_email);
            useremail.requestFocus();
            useremail.setError(msg);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (!ApplicationUtil.eMailValidation(useremail.getText().toString())) {
            msg = getString(R.string.text_enter_valid_email);
            useremail.requestFocus();
            useremail.setError(msg);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (TextUtils.isEmpty(userPassword.getText().toString()) || userPassword.getText() == null) {
            msg = getString(R.string.text_enter_password);
            userPassword.requestFocus();
            userPassword.setError(msg);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }

        else if (userPassword.getText().toString().length() < 4 || userPassword.getText() == null) {
            msg = getString(R.string.text_enter_password_length);
            userPassword.requestFocus();
            userPassword.setError(msg);
            ApplicationUtil.showToast(msg, RegistrationDetails.this);
            check=false;
        }
        else{

            check = true;
        }
        return check;
    }

    private void getDataFromUI(){

        //idTypeText = idType.getSelectedItem().toString();
        idNoText = editTextIDNo.getText().toString();
        name = userName.getText().toString().trim();
        dob = userDOB.getText().toString();
        mobilenumber = userMobile.getText().toString();
        emergemcyContact = emergencyNo.getText().toString();

        houseNumVal = housenum.getText().toString();
        areaVal = area.getText().toString();
        cityVal = city.getText().toString();
        pincodeVal = pincode.getText().toString();
        completeAddress = houseNumVal+" "+areaVal+" "+cityVal+" "+pincodeVal;


        emailId = useremail.getText().toString();
        password = userPassword.getText().toString();



    }


    private void sendUserProfileData(final String usertype){

        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();



        preferences.setPROFILE_PIC(resultProfilePic);
        preferences.setID_TYPE(idTypeID);
        preferences.setGOV_ID(idNoText);
        preferences.setGOV_ID_PIC(resultIDPic);
        preferences.setNAME(name);
        preferences.setDOB(dob);
        preferences.setPRIMARY_MOB(mobilenumber);
        preferences.setSEC_MOB(emergemcyContact);
        preferences.setADDRESS(completeAddress);
        preferences.setMARITAL_STATUS(maritalStausID);
        preferences.setEmail(emailId);
        preferences.setUserid(emailId);
        preferences.setUser_role("R01");
        preferences.setPassword(password);


//        Fragment vehicleDetailsScreen = new PreferenceSelection();
//        getFragmentManager().beginTransaction()
//                //.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
//                .replace(R.id.fragment_place,
//                        vehicleDetailsScreen, "PreferenceSelection").addToBackStack("PreferenceSelection")
//                .commit();


        Fragment fragment = new PreferenceSelection();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
        fragmentTransaction.addToBackStack(TAG_FRAGMENT);
        fragmentTransaction.commit();

        dialog.dismiss();

    }

    private void selectImage(int imageViewID){
        try {
            final int imgViewid = imageViewID;

            final CharSequence[] items = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationDetails.this);
            builder.setTitle("Add Photo");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, imgViewid);
                    }

                    else if (items[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        Fragment fragment = getFragmentManager().findFragmentByTag("registrationFragment");
                        if (fragment == null) {
                            RegistrationDetails.this.startActivityForResult(Intent.createChooser(intent, "Select File"), imgViewid + 1);
                        } else {
                            fragment.startActivityForResult(Intent.createChooser(intent, "Select File"), imgViewid + 1);
                        }

                       /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image*//**//*");
                        onStartActivityFromFragment(Intent.createChooser(intent, "Select File"), imgViewid + 1);
                        */


                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == ApplicationConstants.PROFILE_PICK || requestCode == ApplicationConstants.ID_NO) {

                    onCaptureImageResult(data, resultCode, requestCode);
                }
                else if (requestCode == ApplicationConstants.PROFILE_PICK_GALLERY || requestCode == ApplicationConstants.ID_NO_GALLERY) {

                    onSelectFromGalleryResult(data, resultCode, requestCode);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void onCaptureImageResult(Intent data, int resultcode, int requestcode) {

        Bitmap thumbnail=null;
        try {
       // Uri selectedImageUri = data.getData();
//            Uri selectedImageUri = data.getExtras().get("data");
//
//        String[] projection = { MediaStore.MediaColumns.DATA };
//        Cursor cursor = RegistrationDetails.this.getContentResolver().query(selectedImageUri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        cursor.moveToFirst();
//
//        String selectedImagePath = cursor.getString(column_index);

        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;

            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception er){
            er.printStackTrace();
        }
        if(requestcode== ApplicationConstants.PROFILE_PICK) {
            profile_image.setImageBitmap(thumbnail);
            profilepic = thumbnail;
            resultProfilePic = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        }
        else if(requestcode== ApplicationConstants.ID_NO){
            idNoPick.setImageBitmap(thumbnail);
            idnumber = thumbnail;
            resultIDPic = ApplicationUtil.getInstance().BitMapToString(thumbnail);
        }
    }


    private void onSelectFromGalleryResult(Intent data, int resultcode, int requestcode) throws IOException {

        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = RegistrationDetails.this.getContentResolver().query(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ExifInterface ei = new ExifInterface(selectedImagePath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch(orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                bm = rotateImage(bm, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                bm = rotateImage(bm, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                bm = rotateImage(bm, 270);
                break;
            // etc.
        }

        if(requestcode== ApplicationConstants.PROFILE_PICK_GALLERY) {
            profile_image.setImageBitmap(bm);
            profilepic = bm;
            resultProfilePic = ApplicationUtil.getInstance().BitMapToString(bm);

        }
        else if(requestcode== ApplicationConstants.ID_NO_GALLERY){
            idNoPick.setImageBitmap(bm);
            idnumber = bm;
            resultIDPic = ApplicationUtil.getInstance().BitMapToString(bm);
        }
    }


    private Bitmap rotateImage(Bitmap bm,float angle) {

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap sourceBitmap = bm;
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
    }


    @Override
    public void onBackPressed() {


            // super.onBackPressed();

            Fragment myFragment = (Fragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
            if (myFragment != null && myFragment.isVisible()) {
                // add your code here
                // getFragmentManager().beginTransaction().replace(R.id.fragment_place, new ).addToBackStack(TAG_FRAGMENT).commit();
                // getFragmentManager().beginTransaction().remove(myFragment).commit();
                getFragmentManager().popBackStack();
            }
            else{
                backButtonHandler();
            }


    }

    public void backButtonHandler() {
        try {
            ApplicationUtil.getInstance().showBackDialog(RegistrationDetails.this, "Leave application?", "Are you sure you want to leave the application?");
        }
        catch (Exception er){
            er.printStackTrace();
        }
    }



    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.editTextIDNo:

                   if(idTypeID.equalsIgnoreCase("bhamashahid")) {

                       if (editTextIDNo.getText().toString().trim().length()==15) {
                           validateID();
                           break;
                       }
                       else{
                           if(editTextIDNo.getText().toString().trim().length()!=0) {
                               editTextIDNo.setError(getString(R.string.err_msg_empid));
                               requestFocus(editTextIDNo);
                           }
                       }
                   }
                    else if(idTypeID.equalsIgnoreCase("emitraid")) {


                   }
                    else if(idTypeID.equalsIgnoreCase("adhaarid")) {

                   }
                    else{

                   }

            }
        }
    }



    // get bhamashah data from API

    private void validateID() {

        String url = "https://apitest.sewadwaar.rajasthan.gov.in/app/live/Service/family/details/"+editTextIDNo.getText().toString().trim()+"?client_id=ad7288a4-7764-436d-a727-783a977f1fe1";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {

                            userBhamashah = new UserBhamashahBean();

                            response = response.getJSONObject("hof_Details");

                            if(response.getString("AADHAR_ID").equals("null")) {
                                userBhamashah.setAADHAR_ID(response.getString("AADHAR_ID"));
                            }

                            else{
                                userBhamashah.setAADHAR_ID("");
                            }
                            if(response.getString("FAMILYIDNO").equals("null")) {
                                userBhamashah.setFAMILYIDNO(response.getString("FAMILYIDNO"));
                            }
                            else{
                                userBhamashah.setFAMILYIDNO("");
                            }


                            if(response.getString("FATHER_NAME_ENG").equals("null")) {
                                userBhamashah.setFATHER_NAME_ENG(response.getString("FATHER_NAME_ENG"));
                            }
                            else{
                                userBhamashah.setFATHER_NAME_ENG("");
                            }

                            if(response.getString("FATHER_NAME_HND").equals("null")) {
                                userBhamashah.setFATHER_NAME_HND(response.getString("FATHER_NAME_HND"));
                            }
                            else{
                                userBhamashah.setFATHER_NAME_HND("");
                            }


                            if(response.getString("M_ID").equals("null")) {
                                userBhamashah.setM_ID(response.getString("M_ID"));
                            }

                            else{
                                userBhamashah.setM_ID("");
                            }

                            if(response.getString("SPOUCE_NAME_HND").equals("null")) {
                                userBhamashah.setSPOUCE_NAME_HND(response.getString("SPOUCE_NAME_HND"));
                            }
                            else{
                                userBhamashah.setSPOUCE_NAME_HND("");
                            }


                            if(response.getString("SPOUCE_NAME_ENG").equals("null")) {
                                userBhamashah.setSPOUCE_NAME_ENG(response.getString("SPOUCE_NAME_ENG"));
                            }
                            else{
                                userBhamashah.setSPOUCE_NAME_ENG("");
                            }

                            if(response.getString("MOTHER_NAME_ENG").equals("null")) {
                                userBhamashah.setMOTHER_NAME_ENG(response.getString("MOTHER_NAME_ENG"));
                            }
                            else{
                                userBhamashah.setMOTHER_NAME_ENG("");
                            }

                            if(response.getString("MOTHER_NAME_HND").equals("null")) {
                                userBhamashah.setMOTHER_NAME_HND(response.getString("MOTHER_NAME_HND"));
                            }

                            else{
                                userBhamashah.setMOTHER_NAME_HND("");
                            }


                            userDOB.setText(response.getString("DOB"));
                            area.setText(response.getString("STREET")+" "+", district code: "+response.getString("DISTRICT_CODE")+", ward: "+response.getString("GP_WARD")+" village: "+response.getString("VILLAGE_CODE"));
                            pincode.setText(response.getString("PIN_CODE"));
                            city.setText(response.getString("BLOCK_CITY"));
                            if(response.getString("HOUSE_NUMBER").equals("null")){
                                housenum.setText(response.getString(""));
                            }
                            else{
                                housenum.setText(response.getString("HOUSE_NUMBER"));
                            }

                            userName.setText(response.getString("NAME_ENG"));


//
//                            "AADHAR_ID": "734094301964",
//                                    "STATE": "Rajasthan",
//                                    "MOTHER_NAME_ENG": "ratna devi",
//                                    "DOB": "01-JAN-88",
//                                    "BHAMASHAH_ID": "1067-7PVQ-28383",
//                                    "VILLAGE_CODE": "078167",
//                                    "RSBY_STATUS": "N",
//                                    "STREET": "ward 5",
//                                    "M_ID": "0",
//                                    "FAMILYIDNO": "WDDQMOW",
//                                    "FATHER_NAME_HND": "प्यारेलाल ",
//                                    "PIN_CODE": "321608",
//                                    "GP_WARD": "0810905420",
//                                    "DISTRICT_CODE": "109",
//                                    "SPOUCE_NAME_ENG": "dhoojiram gurjar",
//                                    "IS_RURAL": "Y",
//                                    "HOUSE_NUMBER": null,
//                                    "SPOUCE_NAME_HND": "धूजीराम गुर्जर ",
//                                    "RSBY_URN_NUMBER": null,
//                                    "NFSA_STATUS": "N",
//                                    "NFSA_BPL_NUMBER": null,
//                                    "RATION_CARD_NO": "007816701259",
//                                    "NAME_ENG": "mundar devi gurjar",
//                                    "GENDER": "Female",
//                                    "FATHER_NAME_ENG": "pyarelal",
//                                    "NAME_HND": "मुन्दर देवी गुर्जर ",
//                                    "MOTHER_NAME_HND": "रत्ना देवी ",
//                                    "BLOCK_CITY": "0810905"

                            //System.out.println("bhamashah output"+String.valueOf(hof_Details));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);



    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
