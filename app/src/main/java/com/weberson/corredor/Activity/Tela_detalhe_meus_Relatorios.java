package com.weberson.corredor.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.weberson.corredor.Class.CadastraRelatoriosTurno;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Tela_detalhe_meus_Relatorios extends AppCompatActivity implements View.OnClickListener{


    private EditText manutençao,ativo,equipamento,lider,data,turma,relatorio,pendencia;
    private TextView idRelatorios;
    private CadastraRelatoriosTurno relatorioSelecionado;
    private FirebaseDatabase database;
    private String Idusuariolougado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_detalhe_meus__relatoriosactivity_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes");


        Idusuariolougado = UsuarioFirebase.getIdentificadorUsuario();
        database = FirebaseDatabase.getInstance();
        relatorioSelecionado = getIntent().getParcelableExtra("anuncioSelecionado");

        InicializarComponentes();
        recuperadados();



    }

    private void InicializarComponentes() {

        idRelatorios = (TextView) findViewById(R.id.IdrelatorioA);
        manutençao = (EditText)findViewById(R.id.ManutecaoA);
        ativo = (EditText)findViewById(R.id.AtivoA);
        equipamento = (EditText)findViewById(R.id.EquipamentoA);
        lider = (EditText)findViewById(R.id.LiderA);
        data = (EditText)findViewById(R.id.DataA);
        turma = (EditText)findViewById(R.id.TurmaA);
        relatorio = (EditText)findViewById(R.id.RelatorioA);
        pendencia = (EditText)findViewById(R.id.PendenciaA);


        ///valoresFalso..Editar..................................
        manutençao.setFocusable(false);
        ativo.setFocusable(false);
        equipamento.setFocusable(false);
        lider.setFocusable(false);
        data.setFocusable(false);
        turma.setFocusable(false)
        ;


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){




        }

    }

    private void buttonAlterar() {
        String Ativo = ativo.getText().toString();
        String Data = data.getText().toString();
        String Equipamento = equipamento.getText().toString();
        String Lider = lider.getText().toString();

        String Manutencao = manutençao.getText().toString();
        String idrelatorios = idRelatorios.getText().toString();
        String Relatorios = relatorio.getText().toString();
        String Pendencias = pendencia.getText().toString();
        String Turma = turma.getText().toString();

        alterarDados(Ativo, Data, Equipamento, idrelatorios, Lider, Manutencao, Pendencias, Relatorios, Turma);
        alterarDadosPublicos(Ativo,Data, Equipamento, idrelatorios, Lider, Manutencao, Pendencias, Relatorios, Turma);
    }
    private void buttonRemover(){

        removerFuncionario();


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void alterarDados(String manute, String ativo, String data,String lider,String turma,String relatorio,String pendencia,String id_relatorio,String equipamento){


        DatabaseReference reference =database.getReference().child("meus relatorios").child(Idusuariolougado);

        CadastraRelatoriosTurno funcionario = new CadastraRelatoriosTurno(manute,ativo,data,lider,turma,relatorio,pendencia,id_relatorio,equipamento);


        Map<String, Object> atualizacao = new HashMap<>();


        atualizacao.put(relatorioSelecionado.getIdRelatorio(),funcionario);


        reference.updateChildren(atualizacao).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){



                    Toast.makeText(getBaseContext(),"Sucesso ao Alterar Dados",Toast.LENGTH_LONG).show();



                }else{


                    Toast.makeText(getBaseContext(),"Erro ao Alterar Dados",Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private void alterarDadosPublicos(String manute, String ativo, String data,String lider,String turma,String relatorio,String pendencia,String id_relatorio,String equipamento){


        DatabaseReference reference =database.getReference().child("relatorios").child(relatorioSelecionado.getManuteçao()).child(relatorioSelecionado.getAtivo());

        CadastraRelatoriosTurno funcionario = new CadastraRelatoriosTurno(manute,ativo,data,lider,turma,relatorio,pendencia,id_relatorio,equipamento);


        Map<String, Object> atualizacao = new HashMap<>();


        atualizacao.put(relatorioSelecionado.getIdRelatorio(),funcionario);


        reference.updateChildren(atualizacao).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){



                    Toast.makeText(getBaseContext(),"Sucesso ao Alterar Dados",Toast.LENGTH_LONG).show();


                }else{


                    Toast.makeText(getBaseContext(),"Erro ao Alterar Dados",Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private void removerFuncionario(){


        DatabaseReference reference = database.getReference().child("meus relatorios").child(Idusuariolougado);



        reference.child(relatorioSelecionado.getIdRelatorio()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){


                    Toast.makeText(getBaseContext(),"Sucesso ao Remover Funcionario",Toast.LENGTH_LONG).show();

                    removerRelatoriosPublicos();
                }else{

                    Toast.makeText(getBaseContext(),"Erro ao Remover Funcionario",Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private void removerRelatoriosPublicos(){


        DatabaseReference reference =database.getReference().
                child("relatorios").
                child(relatorioSelecionado.getManuteçao()).
                child(relatorioSelecionado.getAtivo());



        reference.child(relatorioSelecionado.getIdRelatorio()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){


                    Toast.makeText(getBaseContext(),"Sucesso ao Remover Funcionario",Toast.LENGTH_LONG).show();
                    finish();

                }else{

                    Toast.makeText(getBaseContext(),"Erro ao Remover Funcionario",Toast.LENGTH_LONG).show();

                }


            }
        });


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
   public void recuperadados(){

        //Recupera anúncio para exibicao
        relatorioSelecionado = (CadastraRelatoriosTurno) getIntent().getSerializableExtra("anuncioSelecionado");

        if (relatorioSelecionado != null) {
            idRelatorios.setText(relatorioSelecionado.getIdRelatorio());
            manutençao.setText(relatorioSelecionado.getManuteçao());
            ativo.setText(relatorioSelecionado.getAtivo());
            equipamento.setText(relatorioSelecionado.getEquipamento());
            lider.setText(relatorioSelecionado.getLider());
            data.setText(relatorioSelecionado.getData());
            turma.setText(relatorioSelecionado.getTurma());
            relatorio.setText(relatorioSelecionado.getRelatorio());
            pendencia.setText(relatorioSelecionado.getPendencia());


        }

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_atualizar,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()){


            case R.id.atualizar2:

                buttonAlterar();

                return true;


            case R.id.excluir:



                buttonRemover();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
