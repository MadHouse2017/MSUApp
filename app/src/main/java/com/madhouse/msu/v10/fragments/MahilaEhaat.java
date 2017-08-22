package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.ChangePassBean;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ChangePasswordProxie;

/**
 * Created by Krishna on 8/12/2017.
 */
public class MahilaEhaat extends Fragment{

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
        View rootView =  inflater.inflate(R.layout.fragment_mahila_ehaat, container, false);
        pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());

        RelativeLayout ehaat = (RelativeLayout) rootView.findViewById(R.id.view1);
        RelativeLayout govProposal = (RelativeLayout) rootView.findViewById(R.id.view2);
        RelativeLayout newEapp = (RelativeLayout) rootView.findViewById(R.id.view3);
        RelativeLayout existingEcom = (RelativeLayout) rootView.findViewById(R.id.view4);

        ehaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pd.show();
                pd.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay


                Bundle bundle=new Bundle();
                bundle.putString("link", "http://mahilaehaat-rmk.gov.in/en/join-us/");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.ehaat,wv,"WV").addToBackStack("WV").commit();
            }
        });

        govProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                pd.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay


                Bundle bundle=new Bundle();
                bundle.putString("link", "http://mahilaehaat-rmk.gov.in/en/about-e-haat/");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.ehaat,wv,"WV").addToBackStack("WV").commit();
            }
        });

        newEapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                pd.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay


                Bundle bundle=new Bundle();
                bundle.putString("link", "http://mahilaehaat-rmk.gov.in/en/faq/");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.ehaat,wv,"WV").addToBackStack("WV").commit();
            }
        });

        existingEcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                pd.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay


                Bundle bundle=new Bundle();
                bundle.putString("link", "http://mahilaehaat-rmk.gov.in/en/contact-us/");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.ehaat,wv,"WV").addToBackStack("WV").commit();
            }
        });





        return rootView;
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
