package com.example.corredor.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.corredor.Adaptadores.Adapter_lista_Status_equipamentos;
import com.example.corredor.Class.Cadastro_Status_De_Ativos;
import com.example.corredor.Class.RecyclerItemClickListener;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatusEquipamentosActivity extends AppCompatActivity {
    private Button butaomanutençao, butaoativo;
    private Adapter_lista_Status_equipamentos adapter_lista_relatorios_publicos;
    private RecyclerView recyclerView;
    private List<Cadastro_Status_De_Ativos> listarelatorios = new ArrayList<>();
    private DatabaseReference relatoriosPublicosRef;
    private String filtraManutençao = "";
    private String filtraAtivo = "";
    private  boolean filtroPorManutençao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_equipamentosactivity_);

        botoesflutuantes();
        inicializarComponentes();

        //Configurações iniciais

        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("StatusEquipamentos");


        //Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter_lista_relatorios_publicos = new Adapter_lista_Status_equipamentos(listarelatorios, this);
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
                                Cadastro_Status_De_Ativos anuncioSelecionado = listarelatorios.get( position );
                                Intent i = new Intent(StatusEquipamentosActivity.this,
                                        AtualisarStatusEquipamentosActivity.class);
                                i.putExtra("anuncioSelecionado", anuncioSelecionado );
                                startActivity( i );
                                finish();
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


    private void recuperaRelatoriosPublicos() {


        listarelatorios.clear();
        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot estados: dataSnapshot.getChildren()){
                    for (DataSnapshot categorias: estados.getChildren() ){
                        for(DataSnapshot anuncios: categorias.getChildren() ){

                            Cadastro_Status_De_Ativos anuncio = anuncios.getValue(Cadastro_Status_De_Ativos.class);
                            listarelatorios.add( anuncio );

                        }
                    }
                }

                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



    private void recuperaRelatoriosPorManutençao() {

        //Configura nó por categoria
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("StatusEquipamentos")
                .child(filtraManutençao)
                .child( filtraAtivo );

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for(DataSnapshot anuncios: dataSnapshot.getChildren() ){

                    Cadastro_Status_De_Ativos relatorio = anuncios.getValue(Cadastro_Status_De_Ativos.class);
                    listarelatorios.add( relatorio );

                }

                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void filtrarPorManutençao(View view){

        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("Selecione o estado desejado");

        //Configurar spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Configura spinner de estados
        final Spinner spinnerEstado = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] manutençao = getResources().getStringArray(R.array.Ativo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                manutençao
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerEstado.setAdapter( adapter );

        dialogEstado.setView( viewSpinner );

        dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtraManutençao = spinnerEstado.getSelectedItem().toString();
                recuperaRelatoriosPorManutençao();
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
                    recuperaRelatoriosPorAtivo();
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
            Toast.makeText(this, "Escolha primeiro o tipo e Manuteçao!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void recuperaRelatoriosPorAtivo() {

        //Configura nó por estado
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("StatusEquipamentos")
                .child(filtraManutençao);

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for (DataSnapshot categorias: dataSnapshot.getChildren() ){
                    for(DataSnapshot anuncios: categorias.getChildren() ){

                        Cadastro_Status_De_Ativos relatorio = anuncios.getValue(Cadastro_Status_De_Ativos.class);
                        listarelatorios.add( relatorio );

                    }
                }

                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void inicializarComponentes(){


        recyclerView = findViewById(R.id.reciclerStatus);

    }

    private void botoesflutuantes(){

        FloatingActionButton fab = findViewById(R.id.FlutB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StatusEquipamento_Cadastro_AtivosActivity.class));
                finish(); }
        });
        //------------------------------------------------------------------------------------------
        FloatingActionButton aFloatatualizar = findViewById(R.id.atualizarFLB);
        aFloatatualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AtualisarStatusEquipamentosActivity.class));
                finish(); }
        });
    }


}
