package com.example.corredor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.corredor.Class.ClassDF;
import com.example.corredor.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AtualizardadosDF extends AppCompatActivity implements View.OnClickListener {

    private EditText Ddia,Dsemana,Dmes,Dano,D1ia,D1semana,D1mes,D1ano,
    D2dia,D2semana,D2mes,D2ano,D3dia,D3semana,D3mes,D3ano;
    private Button D4atualizar;
    private ClassDF classDFselecionada;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizardados_df);

        getSupportActionBar().setTitle("Atualizar DF");
        inicializarComponentes();
        database = FirebaseDatabase.getInstance();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.D4atualizar:

                Toast.makeText(getBaseContext(),"sucesso",Toast.LENGTH_LONG).show();
                break;


        }

    }


    private void btnalterar(){

        String ddia = Ddia.getText().toString();
        String dsemana = Dsemana.getText().toString();
        String dmes = Dmes.getText().toString();
        String dano = Dano.getText().toString();
        String d1dia = D1ia.getText().toString();
        String d1semana= D1semana.getText().toString();
        String d1mes = D1mes.getText().toString();
        String d1ano = D1ano.getText().toString();
        String d2dia = D2dia.getText().toString();
        String d2semana = D2semana.getText().toString();
        String d2mes = D2mes.getText().toString();
        String d2ano = D2ano.getText().toString();
        String d3dia = D3dia.getText().toString();
        String d3semana = D3semana.getText().toString();
        String d3mes = D3mes.getText().toString();
        String d3ano = D3ano.getText().toString();

        AlterarDados(ddia,dsemana,dmes,dano,d1dia,d1semana,d1mes,d1ano,d2dia,d2semana,d2mes,d2ano,d3dia,d3semana,d3mes,d3ano);

    }

private  void AlterarDados(String ddia,String dsemana,
                           String dmes,String dano, String d1dia, String d1semana, String d1mes,
                           String d1ano,String d2dia, String d2semana,String d2mes,String d2ano,
                           String d3dia,String d3semana,String d3mes,String d3ano){

    DatabaseReference reference = database.getReference().child("DF");




}



    public void inicializarComponentes(){
        Ddia = findViewById(R.id.Ddia);
        Dsemana = findViewById(R.id.Dsemana);
        Dmes = findViewById(R.id.Dmes);
        Dano = findViewById(R.id.Dano);
        D1ia = findViewById(R.id.D1dia);
        D1semana = findViewById(R.id.D1semana);
        D1mes = findViewById(R.id.D1mes);
        D1ano = findViewById(R.id.D1ano);
        D2dia = findViewById(R.id.D2dia);
        D2semana = findViewById(R.id.D2semana);
        D2mes = findViewById(R.id.D2mes);
        D2ano = findViewById(R.id.D2ano);
        D3dia = findViewById(R.id.D3dia);
        D3semana = findViewById(R.id.D3semana);
        D3mes = findViewById(R.id.d3mes);
        D3ano = findViewById(R.id.D3ano);
        D4atualizar = findViewById(R.id.D4atualizar);

        D4atualizar.setOnClickListener(this);







    }
}
