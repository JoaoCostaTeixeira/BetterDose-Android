package com.example.cm_final_proj.farmaciasFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.cm_final_proj.R;

public class farmacia_webview extends Fragment {

    View v;
    String farmname="";
    public farmacia_webview() {
        // Required empty public constructor
    }

    public farmacia_webview(String farmname) {
        this.farmname=farmname;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_farmacia_webview, container, false);
        WebView simpleWebView=(WebView) v.findViewById(R.id.webview);
        simpleWebView.setWebViewClient(new WebViewClient());
        simpleWebView.loadUrl("https://google.com/search?q=" + farmname);
        return v;
    }

}
