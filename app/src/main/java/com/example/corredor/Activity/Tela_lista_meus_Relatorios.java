package com.example.corredor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.example.corredor.Adaptadores.Adapter_lista_Relatorios_Publicos;
import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.RecyclerItemClickListener;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.corredor.Class.UsuarioFirebase.getIdentificadorUsuario;
import static com.example.corredor.Class.UsuarioFirebase.getUsuarioAtual;

public class Tela_lista_meus_Relatorios extends AppCompatActivity {
    private CadastroDeUsuarios usuarios;
    private Adapter_lista_Relatorios_Publicos adapter_lista_relatorios_publicos;
    private RecyclerView recyclerView;
    private List<CadastraRelatoriosTurno> listarelatorios = new ArrayList<>();
    private DatabaseReference relatoriosPublicosRef;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_lista_meus__relatoriosactivity_);

        //Configurações iniciais
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("meus relatorios")
               .child(ConfiguracaoFirebase2 .getIdUsuario() );

      //  permissoesDeUsuarios ();

        inicializarComponentes();
        usuarios = getIntent().getParcelableExtra("usuarios");
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tela_de_Cadastra_Relatorios_Publicos.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter_lista_relatorios_publicos = new Adapter_lista_Relatorios_Publicos(listarelatorios, this);
        recyclerView.setAdapter( adapter_lista_relatorios_publicos );

        //Recupera anúncios para o usuário
        recuperarAnuncios();

        //Adiciona evento de clique no recyclerview
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                CadastraRelatoriosTurno anuncioSelecionado = listarelatorios.get( position );
                                Intent i = new Intent(Tela_lista_meus_Relatorios.this, Tela_detalhe_meus_Relatorios.class);
                                i.putExtra("anuncioSelecionado", anuncioSelecionado ) ;
                                startActivity( i );


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    private void recuperarAnuncios(){

     /*   dialog = new SpotsDialog.Builder()
                .setContext( this )
                .setMessage("Recuperando anúncios")
                .setCancelable( false )
                .build();
        dialog.show();*/

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listarelatorios.clear();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    listarelatorios.add( ds.getValue(CadastraRelatoriosTurno.class) );
                }

                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();

          progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void inicializarComponentes(){

        recyclerView = findViewById(R.id.recyclerRelatorios_Meusrelatorios);
        progressBar = findViewById(R.id.progressBar_meusRelatorios);
    }

/*
    public void permissoesDeUsuarios (){


        FirebaseUser user = getUsuarioAtual();
        if(user != null ){
            Log.d("resultado", "onDataChange: " + getIdentificadorUsuario());
            DatabaseReference usuariosRef = ConfiguracaoFirebase.getFirebase()
                    .child("usuarios")
                    .child( getIdentificadorUsuario() );
            usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("resultado", "onDataChange: " + dataSnapshot.toString() );
                    CadastroDeUsuarios usuario = dataSnapshot.getValue( CadastroDeUsuarios.class );

                    String tipoUsuario = usuario.getTipoUsuario();
                    if( tipoUsuario.equals("M") ){

                        getSupportActionBar().setTitle("SuperUsuarios");

                    }else {

                        getSupportActionBar().setTitle("Usuario normal");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

 */

}
