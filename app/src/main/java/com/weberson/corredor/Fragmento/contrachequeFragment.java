package com.weberson.corredor.Fragmento;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.weberson.corredor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class contrachequeFragment extends Fragment {


    public contrachequeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_contracheque, container, false);
        WebView webView = (WebView)v.findViewById(R.id.idcontrachequeWeb);
        webView.getSettings().setJavaScriptEnabled(true);
       webView.setWebChromeClient(new WebChromeClient());
       // webView.loadUrl("https://zapp.capriza.com/8jgjryxg8wlvte2zh8yzjg?run_anyway=true");
        webView.loadUrl("https://www.google.com");
        return v;
    }

}
