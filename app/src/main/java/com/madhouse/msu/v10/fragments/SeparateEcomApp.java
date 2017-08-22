package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.ChangePassBean;
import com.madhouse.msu.v10.proxies.ChangePasswordProxie;

/**
 * Created by Krishna on 8/12/2017.
 */
public class SeparateEcomApp extends Fragment{

    private TextInputLayout  inputLayoutOldPassword,inputLayoutNewPassword,inputLayoutReNewPassword;
    private EditText inputOldPassword,inputNewPassword,inputReNewPassword;
    private Button openApp;
    private UserPreferences pref;
    private ProgressDialog pd;
    private ChangePasswordProxie changePassProxie;
    private ChangePassBean changePassBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_seperateecomapp, container, false);
        pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());

        openApp = (Button) rootView.findViewById(R.id.openecomm);

        openApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        return rootView;
    }



    public void replceFrag(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.ecommapp, fragment);
        fragmentTransaction.commit();
    }



}
