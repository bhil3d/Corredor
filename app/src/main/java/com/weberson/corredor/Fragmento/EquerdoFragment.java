package com.weberson.corredor.Fragmento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weberson.corredor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EquerdoFragment extends Fragment {



    public EquerdoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equerdo, container, false);

        WebView webView = (WebView)view.findViewById(R.id.idWebferrovia);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://corredor.web.app");
        return view;



    }


}

