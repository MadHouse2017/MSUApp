package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
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
 * Created by Krishna on 4/29/2016.
 */
public class Version extends Fragment{

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
        View rootView =  inflater.inflate( R.layout.fragment_aboutus, container, false);
        /*pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());

        inputLayoutOldPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_old_password);
        inputOldPassword = (EditText) rootView.findViewById(R.id.input_old_password);

        inputLayoutNewPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_new_password);
        inputNewPassword = (EditText) rootView.findViewById(R.id.input_new_password);

        inputLayoutReNewPassword = (TextInputLayout) rootView.findViewById(R.id.reinput_layout_new_password);
        inputReNewPassword = (EditText) rootView.findViewById(R.id.reinput_new_password);

        changePassSubmit = (Button) rootView.findViewById(R.id.btn_change_pass_submit);*/


        return rootView;
    }





}
