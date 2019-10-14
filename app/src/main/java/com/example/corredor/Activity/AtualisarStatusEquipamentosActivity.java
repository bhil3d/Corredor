package com.example.corredor.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.Cadastro_Status_De_Ativos;
import com.example.corredor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AtualisarStatusEquipamentosActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerStatuspl,spinnerStatusManutecao;
    private TextView ativo;
    private TextView spinnerAtivo;
    private TextView idStatus;
    private Button salvar;
    private FirebaseDatabase database;
    private Cadastro_Status_De_Ativos statusSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualisar_status_equipamentosactivity_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        InicializarComponestes();
        carregarDadosSpinner();
        database = FirebaseDatabase.getInstance();
        statusSelecionado = getIntent().getParcelableExtra("anuncioSelecionado");
        recuperadados();
    }



    @Override
    public void onClick(View v) {

switch (v.getId()){

    case R.id.buttonAtualisarSat:

        Toast.makeText(getBaseContext(),"sucesso",Toast.LENGTH_LONG).show();

       // atualisarStatus();


        removerStatus();
        break;

}

    }



    private void atualisarStatus(){
String idstatus = idStatus.getText().toString();
String edit29 = spinnerAtivo.getText().toString();
String te = ativo.getText().toString();
String parado = spinnerStatuspl.getSelectedItem().toString();
String statusmanutecao = spinnerStatusManutecao.getSelectedItem().toString();


atualizardados1(te,edit29,parado,statusmanutecao,idstatus);

    }


    private  void atualizardados1(String te,String edit29,String parado,String statusmautecao, String idstatus){


        DatabaseReference reference = database.getReference().child("StatusEquipamentos").child(statusSelecionado.getAtivo()).child(statusSelecionado.getStatus());

        Cadastro_Status_De_Ativos status = new Cadastro_Status_De_Ativos(te,edit29,parado,statusmautecao, idstatus);

        Map<String, Object> atualizacao = new HashMap<>();

        atualizacao.put(statusSelecionado.getIdStatus(),status);

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
    private Cadastro_Status_De_Ativos configurarCadastro(){

        String ativos = ativo.getText().toString();
        String Spinerativo = spinnerAtivo.getText().toString();
        String Spinerparado = spinnerStatuspl.getSelectedItem().toString();
        String Spinermanutecao = spinnerStatusManutecao.getSelectedItem().toString();

        Cadastro_Status_De_Ativos cadastro = new Cadastro_Status_De_Ativos();
        cadastro.setEquipamento( ativos );
        cadastro.setStatusAtivo( Spinermanutecao );
        cadastro.setStatus(Spinerparado);
        cadastro.setAtivo(Spinerativo);
        return cadastro;

    }

    public void validarDadosAnunci(){

        statusSelecionado = configurarCadastro();
        statusSelecionado.salvar();
        voltaparalistastatus();
    }


    private void voltaparalistastatus(){

        startActivity(new Intent(getApplicationContext(), StatusEquipamentosActivity.class));
        finish(); }


    private void removerStatus(){


        DatabaseReference reference = database.getReference()
                .child("StatusEquipamentos")
                .child(statusSelecionado.getAtivo())
               .child(statusSelecionado.getStatus());



        reference.child(statusSelecionado.getIdStatus())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){


                    Toast.makeText(getBaseContext(),"Sucesso ao Remover Equipamento",Toast.LENGTH_LONG).show();
                    validarDadosAnunci();


                }else{

                    Toast.makeText(getBaseContext(),"Erro ao Remover Equipamento",Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private void carregarDadosSpinner(){


        //Configura spinner de manutençao
        String[] manutençao = getResources().getStringArray(R.array.Status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                manutençao
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerStatuspl.setAdapter( adapter );


        //Configura spinner de turma
        String[] turma = getResources().getStringArray(R.array.Status_Manutencao);
        ArrayAdapter<String> adapterTurma = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                turma
        );
        adapterTurma.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerStatusManutecao.setAdapter( adapterTurma );


    }

    public void recuperadados(){

        //Recupera equipamentos para exibicao
        statusSelecionado = (Cadastro_Status_De_Ativos) getIntent().getSerializableExtra("anuncioSelecionado");

        if (statusSelecionado != null) {
            spinnerAtivo.setText(statusSelecionado.getAtivo());
            ativo.setText(statusSelecionado.getEquipamento());
            idStatus.setText(statusSelecionado.getIdStatus());


        }

    }

    public void InicializarComponestes(){

        idStatus = findViewById(R.id.idStatus);
        spinnerStatuspl = findViewById(R.id.spParadoSat);
        spinnerAtivo = findViewById(R.id.spTEat);
        spinnerStatusManutecao = findViewById(R.id.spSMTsat);
        ativo =(TextView) findViewById(R.id.edit29Sat);
        salvar= findViewById(R.id.buttonAtualisarSat);
        salvar.setOnClickListener(this);


    }


}
