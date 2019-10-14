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

    private RecyclerView recyclerViewConversas;
    private List<TelefonesUteis> listaConversas = new ArrayList<>();
    private AdapterTelefones adapter;
    private DatabaseReference database;
    private DatabaseReference conversasRef;
    private TelefonesUteis anuncioSelecionado;
    private ValueEventListener valueEventListener;

    public EquerdoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equerdo, container, false);
        recyclerViewConversas = view.findViewById(R.id.recyclerT);

        //Configurar adapter
        adapter = new AdapterTelefones(listaConversas, getActivity());

        //Configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversas.setLayoutManager(layoutManager);
        recyclerViewConversas.setHasFixedSize(true);
        recyclerViewConversas.setAdapter(adapter);

        //Configurações iniciais
        conversasRef = ConfiguracaoFirebase2.getFirebase().child("Telefones");
        recuperarConversas();
        return view;
/*
        Intent i = new Intent(getActivity(), ChatActivity.class);
        i.putExtra("chatContato", usuarioSelecionado );
        startActivity( i );

        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefone(), null ));
        startActivity( i );

/*
 */


    }

    private void recuperarConversas() {

        valueEventListener=   conversasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaConversas.clear();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    listaConversas.add( ds.getValue(TelefonesUteis.class) );
                }

                Collections.reverse( listaConversas );
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




}
