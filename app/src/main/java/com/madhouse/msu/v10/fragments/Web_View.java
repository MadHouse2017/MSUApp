package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.madhouse.msu.v10.R;


/**
 * Created by Kartikay on 8/12/17.
 */

public class Web_View extends Fragment {
    private WebView webView;
    private Button close;
    private  String link="http://www.google.com";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.web_view,container,false);

        link = getArguments().getString("link");


        webView = (WebView) view.findViewById(R.id.webView1);
        close = (Button) view.findViewById(R.id.webviewclose);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
        // Allow Zoom in/out controls
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setGeolocationEnabled(true);
        // Zoom out the best fit your screen
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        // Show the progress bar
//        webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                getActivity().setProgress(progress * 100);
//            }
//        });
        webView.loadUrl(link);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });



        return view;
    }

}
