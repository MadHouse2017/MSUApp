package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class ExistingEcomApp extends Fragment{


    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_existingecommapp, container, false);

        dialog = new ProgressDialog(getActivity());

        RelativeLayout flipkart = (RelativeLayout) rootView.findViewById(R.id.flipkart);
        RelativeLayout amazon = (RelativeLayout) rootView.findViewById(R.id.amazon);
        RelativeLayout paytm = (RelativeLayout) rootView.findViewById(R.id.paytm);

        RelativeLayout getflipkart = (RelativeLayout) rootView.findViewById(R.id.getflipkart);
        RelativeLayout getamazon = (RelativeLayout) rootView.findViewById(R.id.getamazon);
        RelativeLayout getpaytm = (RelativeLayout) rootView.findViewById(R.id.getpaytm);

        flipkart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                dialog.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay

                Bundle bundle=new Bundle();
                bundle.putString("link", "https://seller.flipkart.com/");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.existingecom,wv,"WV").addToBackStack("WV").commit();

            }
        });

        amazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                dialog.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay

                Bundle bundle=new Bundle();
                bundle.putString("link", "https://sellercentral.amazon.in/ap/register?openid.return_to=https%3A%2F%2Fsellercentral.amazon.in%2Fsw%2Fin%2FINSSR%2Fstep%2FSignUp%3F_encoding%3DUTF8%26marketplaceId%3DA21TJRUUN4KGV%26passthrough%252Faccount%3Dsoa%26passthrough%252FinitialSessionID%3D259-4763967-2470065%26passthrough%252Fld%3Dinrgooginkenshoo_502X6375_b_c_177527411222%26passthrough%252FmarketplaceID%3DA21TJRUUN4KGV%26passthrough%252FrootMarketplaceID%3D%26passthrough%252FsimplifiedLogin%3D%26passthrough%252FsuperSource%3DOAR%26productTier%3DSILVER%26productType%3DSellOnAmazon%26ref_%3Das_in_soa_benefits_PCT1%26redirectAP%3D1&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=amzn_sw_signup_in&openid.mode=checkid_setup&marketPlaceId=A21TJRUUN4KGV&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.existingecom,wv,"WV").addToBackStack("WV").commit();

            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                dialog.setMessage("Loading..");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000); // 3000 milliseconds delay

                Bundle bundle=new Bundle();
                bundle.putString("link", "https://seller.paytm.com/login");
                Fragment wv = new Web_View();
                wv.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.existingecom,wv,"WV").addToBackStack("WV").commit();

            }
        });

        getflipkart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApp("com.flipkart.seller&hl=en");
            }
        });

        getamazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApp("com.amazon.sellermobile.android&hl=en");
            }
        });


        getpaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApp("com.paytm.merchants&hl=en");
            }
        });



        return rootView;
    }



    private void getApp(String appPackageName){

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}




