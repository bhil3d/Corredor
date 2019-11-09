package com.weberson.corredor.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.weberson.corredor.Adaptadores.Adapter_lista_DF;
import com.weberson.corredor.Class.CadastroDeUsuarios;
import com.weberson.corredor.Class.ClassDF;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.weberson.corredor.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.weberson.corredor.Class.UsuarioFirebase.getIdentificadorUsuario;
import static com.weberson.corredor.Class.UsuarioFirebase.getUsuarioAtual;

public class DfActivity extends AppCompatActivity {

    private Adapter_lista_DF adapter_lista_df;
    private RecyclerView recyclerView;
    private List<ClassDF> listaDf = new ArrayList<>();
    private DatabaseReference relatoriosPublicosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_df);

        verrificarPermissoesDeUsuarios();
        getSupportActionBar().setTitle("DF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicializarComponentes();

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

        recyclerView = findViewById(R.id.recclerdf1);


    }

    private void verrificarPermissoesDeUsuarios() {

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
                    if( tipoUsuario.equals("s") ){

                        Intent i = new Intent(DfActivity.this, Tela_df_resultados.class);
                        startActivity(i);
                        finish();
                    }




                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


}
