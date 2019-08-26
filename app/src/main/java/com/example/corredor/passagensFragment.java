package com.example.corredor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class passagensFragment extends Fragment {


    public passagensFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_passagens, container, false);
        WebView webView = (WebView)v.findViewById(R.id.idWebferrovia);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient());
         //webView.loadUrl("https://tremdepassageiros.vale.com/sgpweb/portal/index.html#/home");
        webView.loadUrl("http://www.vale.com/brasil/PT/business/logistics/railways/trem-passageiros/Paginas/default.aspx");
        return v;
    }

}
