package com.madhouse.msu.v10.adapter;

import android.view.View;

/**
 * Created by Krishna on 1/17/2017.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
