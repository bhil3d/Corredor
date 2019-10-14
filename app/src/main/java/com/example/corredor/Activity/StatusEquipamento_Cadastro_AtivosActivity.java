package com.example.corredor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.Cadastro_Status_De_Ativos;
import com.example.corredor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusEquipamento_Cadastro_AtivosActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerAtivo,spinnerStatuspl,spinnerStatusManutecao;
   private EditText ativo;
   private Button salvar;
    private FirebaseDatabase database;
    private Cadastro_Status_De_Ativos cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_equipamento__cadastro__ativosactivity_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ////////////////////////////////////////////////////////////////////////////////////////////

        InicializarComponestes();
        carregarDadosSpinner();
        database= FirebaseDatabase.getInstance();

    }





//--------------------Tratamentos de Clisks---------------------------------------------------------

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonCDs:

                Toast.makeText(getBaseContext(),"Sucesso",Toast.LENGTH_LONG).show();
                validarDadosAnunci();
                break;
        }



    }


    private Cadastro_Status_De_Ativos configurarCadastro(){

        String ativos = ativo.getText().toString();
        String Spinerativo = spinnerAtivo.getSelectedItem().toString();
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

        cadastro = configurarCadastro();
        cadastro.salvar();



    }






//------------------------Salvar Dados--------------------------------------------------------------


 /*   private void salvarDados(){

       String ativos = ativo.getText().toString();
       String Spinerativo = spinnerAtivo.getSelectedItem().toString();
       String Spinerparado = spinnerStatuspl.getSelectedItem().toString();
       String Spinermanutecao = spinnerStatusManutecao.getSelectedItem().toString();


        salvardados1(ativos,Spinerativo,Spinerparado,Spinermanutecao);

    }


    private void salvardados1(String ativos,String Spinerativo,String Spinerparado,String Spinermanutecao){

    //    DatabaseReference reference = database.getReference().child("StatusDosEquipamentos").child(Spinerativo).child(Spinerparado);
        cadastro.salvar();

     //   Cadastro_Status_De_Ativos status = new Cadastro_Status_De_Ativos(ativos,Spinerativo,Spinerparado,Spinermanutecao);

        //reference.push().setValue(status);


    }

    */

    private void carregarDadosSpinner(){


        //Configura spinner de manutençao
        String[] manutençao = getResources().getStringArray(R.array.Status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                manutençao
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerStatuspl.setAdapter( adapter );



        //Configura spinner de ativo
        String[] ativo = getResources().getStringArray(R.array.Ativo);
        ArrayAdapter<String> adapterAtivo = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                ativo
        );
        adapterAtivo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerAtivo.setAdapter( adapterAtivo );

        //Configura spinner de turma
        String[] turma = getResources().getStringArray(R.array.Status_Manutencao);
        ArrayAdapter<String> adapterTurma = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                turma
        );
        adapterTurma.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerStatusManutecao.setAdapter( adapterTurma );


    }



    public void InicializarComponestes(){


        spinnerStatuspl = findViewById(R.id.spinnerSTc);
        spinnerAtivo = findViewById(R.id.spinnerSTm);
        spinnerStatusManutecao = findViewById(R.id.spinnerSTtipmanu);
        ativo =(EditText) findViewById(R.id.editeSTativo);
        salvar= findViewById(R.id.buttonCDs);
        salvar.setOnClickListener(this);


    }
}
