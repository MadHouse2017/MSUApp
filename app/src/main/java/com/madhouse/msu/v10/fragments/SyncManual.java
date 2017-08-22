package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madhouse.msu.v10.R;

/**
 * Created by Krishna on 4/29/2016.
 */
public class SyncManual extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_syncmanual, container, false);
        return rootView;
       }


}
