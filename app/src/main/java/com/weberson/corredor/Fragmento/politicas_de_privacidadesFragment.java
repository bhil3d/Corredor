package com.weberson.corredor.Fragmento;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weberson.corredor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class politicas_de_privacidadesFragment extends Fragment {


    public politicas_de_privacidadesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_politicas_privacidades, container, false);

        WebView webView = (WebView)v.findViewById(R.id.idWebpoliticasP);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://corredor.web.app/politicas_de_privacidades.html");
        return v;
    }

}
