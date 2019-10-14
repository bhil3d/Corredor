package com.example.corredor.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.corredor.Adaptadores.Adapter_lista_Relatorios_Publicos;
import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.RecyclerItemClickListener;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tela_Lista_dos_Relatorio_Publicos extends AppCompatActivity {

    private Button butaomanutençao, butaoativo;
    private FirebaseAuth autenticacao;
    private Adapter_lista_Relatorios_Publicos adapter_lista_relatorios_publicos;
    private RecyclerView recyclerView;
private List<CadastraRelatoriosTurno>listarelatorios = new ArrayList<>();
private DatabaseReference relatoriosPublicosRef;
private String filtraManutençao = "";
private String filtraAtivo = "";
private  boolean filtroPorManutençao = false;
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listade_relatoriosactivity_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializarComponentes();


        //Configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("relatorios");


        //Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter_lista_relatorios_publicos = new Adapter_lista_Relatorios_Publicos(listarelatorios, this);
        recyclerView.setAdapter( adapter_lista_relatorios_publicos );

recuperaRelatoriosPublicos();

//Aplicar evento de clique
       recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                CadastraRelatoriosTurno anuncioSelecionado = listarelatorios.get( position );
                                Intent i = new Intent(Tela_Lista_dos_Relatorio_Publicos.this, Tela_Detalhes_Relatorios_Publicos.class);
                                i.putExtra("anuncioSelecionado", anuncioSelecionado );
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

       public void filtrarPorManutençao(View view){

        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("Selecione o estado desejado");

        //Configurar spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Configura spinner de estados
        final Spinner spinnerEstado = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] estados = getResources().getStringArray(R.array.Manuteçao);
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
            String[] estados = getResources().getStringArray(R.array.Ativo);
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
                .child("relatorios")
                .child(filtraManutençao)
                .child( filtraAtivo );

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for(DataSnapshot anuncios: dataSnapshot.getChildren() ){

                    CadastraRelatoriosTurno anuncio = anuncios.getValue(CadastraRelatoriosTurno.class);
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
                .child("relatorios")
                .child(filtraManutençao);

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for (DataSnapshot categorias: dataSnapshot.getChildren() ){
                    for(DataSnapshot anuncios: categorias.getChildren() ){

                        CadastraRelatoriosTurno anuncio = anuncios.getValue(CadastraRelatoriosTurno.class);
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

                            CadastraRelatoriosTurno anuncio = anuncios.getValue(CadastraRelatoriosTurno.class);
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

/*public void adicionarRelatorios(View view){

    Intent i = new Intent(Tela_Lista_dos_Relatorio_Publicos.this, Tela_de_Cadastra_Relatorios_Publicos.class);
    startActivity( i );
    finish();
}*/


    public void inicializarComponentes(){


        recyclerView = findViewById(R.id.recyclerRelatorios_Meusrelatorios);
        progressBar = findViewById(R.id.progressBar_Relatorios);

    }

}
