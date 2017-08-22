package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.ChangePassBean;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ChangePasswordProxie;

/**
 * Created by Krishna on 4/29/2016.
 */
public class ChangePassword extends Fragment{

    private TextInputLayout  inputLayoutOldPassword,inputLayoutNewPassword,inputLayoutReNewPassword;
    private EditText inputOldPassword,inputNewPassword,inputReNewPassword;
    private Button changePassSubmit;
    private UserPreferences pref;
    private ProgressDialog pd;
    private ChangePasswordProxie changePassProxie;
    private ChangePassBean changePassBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_changepassword, container, false);
        pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());

        inputLayoutOldPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_old_password);
        inputOldPassword = (EditText) rootView.findViewById(R.id.input_old_password);

        inputLayoutNewPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_new_password);
        inputNewPassword = (EditText) rootView.findViewById(R.id.input_new_password);

        inputLayoutReNewPassword = (TextInputLayout) rootView.findViewById(R.id.reinput_layout_new_password);
        inputReNewPassword = (EditText) rootView.findViewById(R.id.reinput_new_password);

        changePassSubmit = (Button) rootView.findViewById(R.id.btn_change_pass_submit);

        inputOldPassword.addTextChangedListener(new MyTextWatcher(inputOldPassword));
        inputNewPassword.addTextChangedListener(new MyTextWatcher(inputNewPassword));
        inputReNewPassword.addTextChangedListener(new MyTextWatcher(inputReNewPassword));

        changePassSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChangePasswordRequest();
            }
        });

        return rootView;
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

                case R.id.input_old_password:
                    validateOldPassword();
                    break;
                case R.id.input_new_password:
                    validateNewPassword();
                    break;
                case R.id.reinput_new_password:
                    validateReNewPassword();
                    break;
            }
        }
    }


    private boolean validateOldPassword() {
        if (inputOldPassword.getText().toString().trim().isEmpty()) {
            inputLayoutOldPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputOldPassword);
            return false;
        }

       /*  if(!inputNewPassword.getText().toString().trim().equals(inputReNewPassword.getText().toString().trim())){
            inputLayoutReNewPassword.setError(getString(R.string.err_msg_re_password));
            requestFocus(inputReNewPassword);
            return false;
        }*/

        else {
            inputLayoutOldPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNewPassword() {

        if (inputNewPassword.getText().toString().trim().isEmpty()) {
            inputLayoutNewPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputNewPassword);
            return false;
        }

        else {
            inputLayoutNewPassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateReNewPassword() {

        if (inputReNewPassword.getText().toString().trim().isEmpty()) {
            inputLayoutReNewPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputReNewPassword);
            return false;
        }

        if(!inputNewPassword.getText().toString().trim().equals(inputReNewPassword.getText().toString().trim())){
            inputLayoutReNewPassword.setError(getString(R.string.err_msg_re_password));
            requestFocus(inputReNewPassword);
            return false;
        }

        else {
            inputLayoutReNewPassword.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void submitChangePasswordRequest() {

        if (!validateOldPassword()) {
            return;
        }
        if (!validateNewPassword()) {
            return;
        }
        if (!validateReNewPassword()) {
            return;
        }


        AsyncChangePass task = new AsyncChangePass();
        //Call execute
        task.execute();


        android.app.AlertDialog ad = ApplicationUtil.getInstance().showAlertDialogWithTitle("Successful","Your password successfully changed. You can Login with your new password",
                "","Dismiss", R.layout.alertdialog_message_description, getActivity());

        ad.show();

        inputOldPassword.setText("");
        inputNewPassword.setText("");
        inputReNewPassword.setText("");
        /*Intent mainIntent = new Intent(ChangePassword.this,MainActivity.class);
        LoginScreen.this.startActivity(mainIntent);
        LoginScreen.this.finish();*/
    }


    private class AsyncChangePass extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {
                changePassBean = ApplicationUtil.getInstance().getWebservice().ChangePassword(changePassProxie, getActivity());
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

            pd.setMessage("Loading..");
            pd.show();

            changePassProxie = new ChangePasswordProxie();
            changePassProxie.setStrEmpId(pref.getUserid());
            changePassProxie.setStrOldPass(inputOldPassword.getText().toString().trim());
            changePassProxie.setStrNewPass(inputNewPassword.getText().toString().trim());
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Make Progress Bar invisible
            //loginStatus = inputEmpid.getText().toString();

            pd.dismiss();
            //Error status is false

            if (changePassBean != null) {
                //Based on Boolean value returned from WebService
                   /* if (user.getEMPROLE().equals("admin")) {
                        //Navigate to Home Screen
                        mainIntent.putExtra("empType", "admin");
                        LoginScreen.this.startActivity(mainIntent);
                        LoginScreen.this.finish();
                    } else if (loginStatus.equals("employee")) {
                        //Navigate to Home Screen

                    } else {
                        //Set Error message
                        // Toast.makeText(LoginScreen.this,"Login Failed, try again",Toast.LENGTH_LONG).show();

                        mainIntent.putExtra("empType", "employee");
                        LoginScreen.this.startActivity(mainIntent);
                        LoginScreen.this.finish();
                    }*/

                //Error status is true
            }
            else{

                Toast.makeText(getActivity(),"Error occured in invoking webservice", Toast.LENGTH_LONG).show();
            }
            //Re-initialize Error Status to False
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }






}
