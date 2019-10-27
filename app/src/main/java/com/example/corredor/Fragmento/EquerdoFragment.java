package com.example.corredor.Fragmento;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.corredor.Adaptadores.AdapterTelefones;
import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.RecyclerItemClickListener;
import com.example.corredor.Class.TelefonesUteis;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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


