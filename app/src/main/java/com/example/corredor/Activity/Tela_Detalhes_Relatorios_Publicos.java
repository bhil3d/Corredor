package com.example.corredor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.R;

public class Tela_Detalhes_Relatorios_Publicos extends AppCompatActivity {

    private TextView manutençao,ativo,equipamento,lider,data,turma,relatorio,pendencia;
    private CadastraRelatoriosTurno relatorioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_detalhes_relatorios_publicosactivity_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes");

        inicializarComponentes();

        //Recupera anúncio para exibicao
        relatorioSelecionado = (CadastraRelatoriosTurno) getIntent().getSerializableExtra("anuncioSelecionado");

        if (relatorioSelecionado != null) {

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


    private void inicializarComponentes(){

        manutençao = findViewById(R.id.editeManutencao);
        ativo = findViewById(R.id.textViewAtivo);
        equipamento = findViewById(R.id.textViewEquipamento);
        lider = findViewById(R.id.textViewLider);
        data = findViewById(R.id.textViewdata);
        turma = findViewById(R.id.textViewTurma);
        relatorio = findViewById(R.id.textviewRelatorio);
        pendencia = findViewById(R.id.textviewPendencia);
    }


}
