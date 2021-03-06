package com.madhouse.msu.v10.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.madhouse.msu.v10.R;


/**
 * Created by Krishna.
 */
public class SpinnerAdapterHelpdesk extends ArrayAdapter<String> {

    private String[] spinnervalues;
    private String[] spinnerSubs;
   // private String[] x = new String[1];

   /* public SpinnerAdapter(Context ctx, int txtViewResourceId, String[] objects, String[] objectsSub, int[] objectImg) {
        super(ctx, txtViewResourceId, objects);

        this.spinnervalues = objects;
        this.spinnerSubs = objectsSub;
        this.spinnerImg = objectImg;
    }*/

    public SpinnerAdapterHelpdesk(Context ctx, int txtViewResourceId, String[] objects, String[] objectsSub) {
        super(ctx, txtViewResourceId, objects);

        this.spinnervalues = objects;
        this.spinnerSubs = objectsSub;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // LayoutInflater inflater = getLayoutInflater();
        View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);

        TextView main_text = (TextView) mySpinner.findViewById(R.id.text_main_seen);
        main_text.setText(spinnervalues[position]);

        TextView subSpinner = (TextView) mySpinner.findViewById(R.id.sub_text_seen);
        subSpinner.setText(spinnerSubs[position]);

        return mySpinner;
    }

}
