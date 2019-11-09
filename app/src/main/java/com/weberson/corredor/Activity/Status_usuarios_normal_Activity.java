package com.weberson.corredor.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.weberson.corredor.Adaptadores.Adapter_lista_Status_equipamentos;
import com.weberson.corredor.Class.CadastroDeUsuarios;
import com.weberson.corredor.Class.Cadastro_Status_De_Ativos;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.weberson.corredor.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.weberson.corredor.Class.UsuarioFirebase.getIdentificadorUsuario;
import static com.weberson.corredor.Class.UsuarioFirebase.getUsuarioAtual;

public class Status_usuarios_normal_Activity extends AppCompatActivity {

    private Button butaomanutençao, butaoativo;
    private Adapter_lista_Status_equipamentos adapter_lista_relatorios_publicos;
    private RecyclerView recyclerView;
    private List<Cadastro_Status_De_Ativos> listarelatorios = new ArrayList<>();
    private DatabaseReference relatoriosPublicosRef;
    private String filtraManutençao = "";
    private ProgressBar progressBar;
    private String filtraAtivo = "";
    private  boolean filtroPorManutençao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_usuarios_normal_);

        verrificarPermissoesDeUsuarios();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicializarComponentes();

        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("StatusEquipamentos");

        //Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter_lista_relatorios_publicos = new Adapter_lista_Status_equipamentos(listarelatorios, this);
        recyclerView.setAdapter( adapter_lista_relatorios_publicos );

        recuperaRelatoriosPublicos();


    }




    public void filtrarPorManutençao(View view){

        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("Selecione o estado desejado");

        //Configurar spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Configura spinner de estados
        final Spinner spinnerEstado = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] estados = getResources().getStringArray(R.array.Ativo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerEstado.setAdapter( adapter );

        dialogEstado.setView( viewSpinner );

        dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtraManutençao = spinnerEstado.getSelectedItem().toString();
                recuperarAnunciosPorEstado();
                filtroPorManutençao = true;
            }
        });

        dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = dialogEstado.create();
        dialog.show();

    }

    public void filtrarPorAtivo(View view){

        if( filtroPorManutençao == true ){

            AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
            dialogEstado.setTitle("Selecione a categoria desejada");

            //Configurar spinner
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

            //Configura spinner de categorias
            final Spinner spinnerCategoria = viewSpinner.findViewById(R.id.spinnerFiltro);
            String[] estados = getResources().getStringArray(R.array.Status);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item,
                    estados
            );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            spinnerCategoria.setAdapter( adapter );

            dialogEstado.setView( viewSpinner );

            dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filtraAtivo = spinnerCategoria.getSelectedItem().toString();
                    recuperarAnunciosPorCategoria();
                }
            });

            dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = dialogEstado.create();
            dialog.show();

        }else {
            Toast.makeText(this, "Escolha primeiro uma região!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void recuperarAnunciosPorCategoria(){

        progressBar.setVisibility(View.VISIBLE);

        //Configura nó por categoria
        relatoriosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("StatusEquipamentos")
                .child(filtraManutençao)
                .child( filtraAtivo );

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for(DataSnapshot anuncios: dataSnapshot.getChildren() ){

                    Cadastro_Status_De_Ativos anuncio = anuncios.getValue(Cadastro_Status_De_Ativos.class);
                    listarelatorios.add( anuncio );

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

    public void recuperarAnunciosPorEstado(){

        progressBar.setVisibility(View.VISIBLE);

        //Configura nó por estado
        relatoriosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("StatusEquipamentos")
                .child(filtraManutençao);

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for (DataSnapshot categorias: dataSnapshot.getChildren() ){
                    for(DataSnapshot anuncios: categorias.getChildren() ){

                        Cadastro_Status_De_Ativos anuncio = anuncios.getValue(Cadastro_Status_De_Ativos.class);
                        listarelatorios.add( anuncio );

                    }
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

    private void recuperaRelatoriosPublicos() {


        listarelatorios.clear();
        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                for(DataSnapshot manutecao: dataSnapshot.getChildren()){
                    for (DataSnapshot categorias: manutecao.getChildren() ){
                        for(DataSnapshot anuncios: categorias.getChildren() ){

                            Cadastro_Status_De_Ativos anuncio = anuncios.getValue(Cadastro_Status_De_Ativos.class);
                            listarelatorios.add( anuncio );



                        }
                    }
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

    private void inicializarComponentes() {

        recyclerView = findViewById(R.id.recicler2);
        progressBar= findViewById(R.id.progrebar2);

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

                        Intent i = new Intent(Status_usuarios_normal_Activity.this, StatusEquipamentosActivity.class);
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
