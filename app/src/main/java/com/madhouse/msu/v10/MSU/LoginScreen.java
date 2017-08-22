package com.madhouse.msu.v10.MSU;

/**
 * Created by Krishna on 4/28/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.User;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ValidateUserProxie;

public class LoginScreen extends Activity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText inputEmpid, inputEmail, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnSignIn;
    private TextView forgetPassword, signup;
    private String webservice_URL="http://10.0.2.2:8084/bses_IM/IMWebService/authenticateUser";
    public static final String KEY_EMPID = "username";
    public static final String KEY_PASSWORD = "password";
    private String empId="", empPassword="";
    private String loginStatus;
    public ProgressBar webservicePG;

    public Intent mainIntent;
    private User user;
    private String regGcmId="";
    private int versionNumber = 1;
    private String currentAppVersion = "";
    private UserPreferences userPreferences;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_screen);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatedlayout);
        webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        TextView ctoolbar_title = (TextView) toolbar.findViewById(R.id.ctoolbar_title);

        ctoolbar_title.setText(R.string.msu_login_heading);

        userPreferences = UserPreferences.getInstance(LoginScreen.this);
        regGcmId = userPreferences.getDevice_fcm_id();

        PackageInfo pinfo;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = pinfo.versionCode;
            currentAppVersion = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // setSupportActionBar(toolbar);
        init();

        setListeners();
       /* btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm(view);
            }
        });*/
    }

    private void init(){
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_empid);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutEmail.setVisibility(View.GONE);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);


        inputEmpid = (EditText) findViewById(R.id.input_empid);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        signup = (TextView) findViewById(R.id.signup);
        TextView gap = (TextView) findViewById(R.id.gap);

        if(userPreferences.getIS_REGISTERED().equalsIgnoreCase("YES")){
            signup.setVisibility(View.GONE);
            gap.setVisibility(View.GONE);
        }
        else{
            signup.setVisibility(View.VISIBLE);
            gap.setVisibility(View.VISIBLE);
        }


    }

    private void setListeners(){
        btnSignIn.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        signup.setOnClickListener(this);

        inputEmpid.addTextChangedListener(new MyTextWatcher(inputEmpid));
       // inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_signin:
                // do your code
                submitForm(v);
                break;

            case R.id.forgetPassword:
                // do your code
                forgetPassword();
                break;

            case R.id.signup:
                // do your code
                registerScreen();
                break;

            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Validating form
     */


    private void submitForm(View view) {

       /* if (!validateEmail()) {
            return;
        }*/

        if (!validateEmpid()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        ApplicationUtil.getInstance().hideSoftKeyBoard(LoginScreen.this);
        sendLoginDataToWeb();
    }

    private void forgetPassword() {

        Intent mainIntent = new Intent(LoginScreen.this,ForgetPassword.class);
        LoginScreen.this.startActivity(mainIntent);
       // LoginScreen.this.finish();
    }

    private void registerScreen() {

        Intent mainIntent = new Intent(LoginScreen.this, RegistrationDetails.class);
        startActivity(mainIntent);
        finish();
        // LoginScreen.this.finish();
    }

    private boolean validateEmpid() {
        if (inputEmpid.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_empid));
            requestFocus(inputEmpid);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

   /* private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }*/

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

   /* private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.input_empid:
                    validateEmpid();
                    break;
                /*case R.id.input_email:
                    validateEmail();
                    break;*/
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }



    public void backButtonHandler() {
        try {
            ApplicationUtil.getInstance().showBackDialog(LoginScreen.this, "Leave application?", "Are you sure you want to leave the application?");
        }
        catch (Exception er){
            er.printStackTrace();
        }
    }

   private void sendLoginDataToWeb(){

        empId = inputEmpid.getText().toString().trim();
        empPassword = inputPassword.getText().toString().trim();




       //userPreferences.setUserid(empId);
       //userPreferences.setPassword(empPassword);

       //Create instance for AsyncCallWS
       AsyncCallWS task = new AsyncCallWS();
       //Call execute
       task.execute();

       /*StringRequest stringRequest = new StringRequest(Request.Method.POST, webservice_URL,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Toast.makeText(LoginScreen.this,response,Toast.LENGTH_LONG).show();
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(LoginScreen.this,error.toString(),Toast.LENGTH_LONG).show();
                   }
               }){
           @Override
           protected Map<String,String> getParams(){
               Map<String,String> params = new HashMap<String, String>();
               params.put(KEY_EMPID, empId);
               params.put(KEY_PASSWORD, empPassword);
               return params;
           }

       };

       AppController.getInstance().addToRequestQueue(stringRequest);*/
       /*RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(stringRequest);*/


   }

    private class AsyncCallWS extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            try {

                ValidateUserProxie validateUserProxie = new ValidateUserProxie();
                validateUserProxie.set_sPass(empPassword);
                validateUserProxie.set_sUser(empId);

                if (regGcmId != null) {
                    validateUserProxie.set_sGcmId(regGcmId);
                } else {
                    if (userPreferences.getDevice_fcm_id() != null) {
                        validateUserProxie.set_sGcmId(userPreferences.getDevice_fcm_id());
                    }
                }
                //validateUserProxie.setStrImeiNo(ApplicationUtil.getInstance().getImeiNo(LoginScreen.this));

                //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
                if (ApplicationUtil.getInstance().checkInternetConnection(LoginScreen.this)) {

                    user = ApplicationUtil.getInstance().getWebservice().validateUser(validateUserProxie, LoginScreen.this);
                }
            }
            catch (ApplicationException e) {
                e.printStackTrace();
            }

            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webservicePG.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Make Progress Bar invisible
            //loginStatus = inputEmpid.getText().toString();
            try{
                    webservicePG.setVisibility(View.INVISIBLE);
                    mainIntent = new Intent(LoginScreen.this,MainActivity.class);

                    //Error status is false

                        if (user != null) {
                            //Based on Boolean value returned from WebService


                            userPreferences.setISLOGGEDIN("YES");
                            userPreferences.setIS_REGISTERED("YES");

                            userPreferences.setUser_role(user.getUSER_ROLE());
                            userPreferences.setDOB(user.getDOB());
                            userPreferences.setUserid(user.getEmailId());

                            userPreferences.setADDRESS(user.getADDRESS());
                            userPreferences.setANS1(user.getANS1());
                            userPreferences.setANS2(user.getANS2());
                            userPreferences.setANS3(user.getANS3());
                            userPreferences.setANS4(user.getANS4());
                            userPreferences.setANS5(user.getANS5());
                            userPreferences.setDevice_fcm_id(user.getGcmId());
                            userPreferences.setEmail(user.getEmailId());
                            userPreferences.setNAME(user.getUserName());
                            userPreferences.setGOV_ID(user.getIdNumber());
                            userPreferences.setGOV_ID_PIC(user.getIdPhoto());
                            userPreferences.setID_TYPE(user.getIdType());
                            userPreferences.setMARITAL_STATUS(user.getMaritalStatus());
                            userPreferences.setPRIMARY_MOB(user.getPrimMobileNo());
                            userPreferences.setPassword(user.getPassword());
                            userPreferences.setPREFERENCE_HOBBY(user.getPREFERENCE_HOBBY());
                            userPreferences.setPREFERENCE_PROFICIENCY(user.getHOBBY_PROFICIENCY());
                            userPreferences.setPROFILE_PIC(user.getPHOTO());
                            userPreferences.setSEC_MOB(user.getSecMobileNo());
                            userPreferences.setUNIQUE_ID(user.getUniqueLoginID());


                           if (user.getUSER_ROLE().equalsIgnoreCase("R001")) {
                                //Navigate to Home Screen
                                mainIntent.putExtra("empType", "admin");
                                LoginScreen.this.startActivity(mainIntent);
                                LoginScreen.this.finish();
                            }

                           else {
                                //Set Error message
                                // Toast.makeText(LoginScreen.this,"Login Failed, try again",Toast.LENGTH_LONG).show();
                                mainIntent.putExtra("empType", "admin");
                                LoginScreen.this.startActivity(mainIntent);
                                LoginScreen.this.finish();
                            }

                        }
                    else{

                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Server Not Responding!", Snackbar.LENGTH_LONG)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sendLoginDataToWeb();
                                        }
                                    });

                           // Changing message text color
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));

                            // Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(getResources().getColor(R.color.colorAccent));
                            snackbar.show();
                            //CSnackBar.getInstance().showSnackBarError(findViewById(R.id.mainLayout),"No Internet connection available",m_Context);
                          //  Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();//*
                      //  Toast.makeText(LoginScreen.this,"Server Not Responding | Please Try Again", Toast.LENGTH_LONG).show();
                    }
                    //Re-initialize Error Status to False


                }
                catch (Exception er){

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Some Error Occured!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sendLoginDataToWeb();
                                }
                            });

                    // Changing message text color
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();

                    er.printStackTrace();
                }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }


}