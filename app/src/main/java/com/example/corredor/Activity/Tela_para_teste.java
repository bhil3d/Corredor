package com.example.corredor.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.Class.Util;
import com.example.corredor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tela_para_teste extends AppCompatActivity implements View.OnClickListener {

    private Button button_excluir;
    private TextView idrelatorio;
    private String Idusuariolougado;
    private EditText editText_manutecao;
    private EditText editText_equipamento;
    private EditText editText_ativo;
    private EditText editText_lider;
    private EditText editText_data;
    private EditText editText_turma;
    private EditText editText_relatorio;
    private EditText editText_pendencia;
    private Button button_Alterar;
    private List<CadastraRelatoriosTurno> cadastros = new ArrayList<CadastraRelatoriosTurno>();
    private FirebaseDatabase database;
    private CadastraRelatoriosTurno funcionarioSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_para_teste);

        idrelatorio=(TextView) findViewById(R.id.textViewidterra);
        editText_manutecao = (EditText)findViewById(R.id.editTextManut);
        editText_equipamento = (EditText)findViewById(R.id.editText10EP);
        editText_ativo = (EditText)findViewById(R.id.editTextativo);
        editText_lider = (EditText)findViewById(R.id.editTextlider);
        editText_data = (EditText)findViewById(R.id.editTextdata);
        editText_turma = (EditText)findViewById(R.id.editTexturma);
        editText_relatorio = (EditText)findViewById(R.id.editTextrelatorio);
        editText_pendencia = (EditText)findViewById(R.id.editTextpendencia);
        button_Alterar = (Button)findViewById(R.id.buttonsalterar);
        button_excluir = (Button)findViewById(R.id.buttonExcluir);
        button_Alterar.setOnClickListener(this);
        button_excluir.setOnClickListener(this);
        Idusuariolougado = UsuarioFirebase.getIdentificadorUsuario();
        database = FirebaseDatabase.getInstance();
        funcionarioSelecionado = getIntent().getParcelableExtra("anuncioSelecionado");
        recuperadados();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonsalterar:


                buttonAlterar();

                break;

            case R.id.buttonExcluir:


                removerFuncionario();

                break;

        }

    }

    private void buttonAlterar(){

        String id_relatorio = idrelatorio.getText().toString();
        String equipamento = editText_equipamento.getText().toString();
        String manute = editText_manutecao.getText().toString();
        String ativo = editText_ativo.getText().toString();
        String data = editText_data.getText().toString();
        String lider = editText_lider.getText().toString();
        String turma = editText_turma.getText().toString();
        String relatorio = editText_relatorio.getText().toString();
        String pendencia = editText_pendencia.getText().toString();

        alterarDados(manute,ativo,data,lider,turma,relatorio,pendencia,id_relatorio,equipamento);
        alterarDadosPublicos(manute,ativo,data,lider,turma,relatorio,pendencia,id_relatorio,equipamento);

    }



    private void alterarDados(String manute, String ativo, String data,String lider,String turma,String relatorio,String pendencia,String id_relatorio,String equipamento){


        DatabaseReference reference =database.getReference().child("meus relatorios").child(Idusuariolougado);

        CadastraRelatoriosTurno funcionario = new CadastraRelatoriosTurno(manute,ativo,data,lider,turma,relatorio,pendencia,id_relatorio,equipamento);


        Map<String, Object> atualizacao = new HashMap<>();


        atualizacao.put(funcionarioSelecionado.getIdRelatorio(),funcionario);


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


        DatabaseReference reference =database.getReference().child("relatorios").child(funcionarioSelecionado.getManuteçao()).child(funcionarioSelecionado.getAtivo());

        CadastraRelatoriosTurno funcionario = new CadastraRelatoriosTurno(manute,ativo,data,lider,turma,relatorio,pendencia,id_relatorio,equipamento);


        Map<String, Object> atualizacao = new HashMap<>();


        atualizacao.put(funcionarioSelecionado.getIdRelatorio(),funcionario);


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



        reference.child(funcionarioSelecionado.getIdRelatorio()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {


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
                child(funcionarioSelecionado.getManuteçao()).
                child(funcionarioSelecionado.getAtivo());



        reference.child(funcionarioSelecionado.getIdRelatorio()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {


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
  public void recuperadados(){

        //Recupera anúncio para exibicao
      funcionarioSelecionado = (CadastraRelatoriosTurno) getIntent().getSerializableExtra("anuncioSelecionado");

        if (funcionarioSelecionado != null) {
          //  idusuario.setText(relatorioSelecionado.getIdUsuarios());
         idrelatorio.setText(funcionarioSelecionado.getIdRelatorio());
            editText_manutecao.setText(funcionarioSelecionado.getManuteçao());
            editText_ativo.setText(funcionarioSelecionado.getAtivo());
            editText_equipamento.setText(funcionarioSelecionado.getEquipamento());
            editText_lider.setText(funcionarioSelecionado.getLider());
            editText_data.setText(funcionarioSelecionado.getData());
            editText_turma.setText(funcionarioSelecionado.getTurma());
            editText_relatorio.setText(funcionarioSelecionado.getRelatorio());
            editText_pendencia.setText(funcionarioSelecionado.getPendencia());


        }

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



}
