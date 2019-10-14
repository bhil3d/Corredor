package com.example.corredor.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.corredor.Adaptadores.Adapter_lista_DF;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.ClassDF;
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

public class Tela_df_resultados extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private Adapter_lista_DF adapter_lista_df;
    private RecyclerView recyclerView;
    private List<ClassDF> listaDf = new ArrayList<>();
    private DatabaseReference relatoriosPublicosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_df_resultados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializarComponentes();


       // floatingActionButton.setVisibility(View.GONE);


        FloatingActionButton fab = findViewById(R.id.FBdf);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AtualizardadosDF.class));
            }


        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

getSupportActionBar().setTitle("DF");

        //Configurações iniciais
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("DF");

        //Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter_lista_df = new Adapter_lista_DF(listaDf, this);
        recyclerView.setAdapter( adapter_lista_df );

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


                                ClassDF anuncioSelecionado = listaDf.get( position );
                                Intent i = new Intent(Tela_df_resultados.this, AtualizardadosDF.class);
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

                listaDf.clear();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    listaDf.add( ds.getValue(ClassDF.class) );
                }

                Collections.reverse( listaDf );
                adapter_lista_df.notifyDataSetChanged();

                //   dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void inicializarComponentes(){

        recyclerView = findViewById(R.id.ReciclerDF);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.FBdf);


    }




}
