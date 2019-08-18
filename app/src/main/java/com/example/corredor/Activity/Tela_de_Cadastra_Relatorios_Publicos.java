package com.example.corredor.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.R;

public class Tela_de_Cadastra_Relatorios_Publicos extends AppCompatActivity implements View.OnClickListener {


    private Spinner spinnerManutençao,spinnerAtivo,spinnerTurma;
    private TextInputEditText Equipamento;
    private TextInputEditText Lider;
    private EditText Data;
    private TextInputEditText relatorio;
    private TextInputEditText pendendia;
    private CadastraRelatoriosTurno castro;
    private String Idusuariolougado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra__relatoriosctivity);

        getSupportActionBar().setTitle("Novo relatorio");

        InicializarComponestes();
        carregarDadosSpinner();
        Idusuariolougado = UsuarioFirebase.getIdentificadorUsuario();

    }


    private CadastraRelatoriosTurno configurarCadastro(){

        String manutençao = spinnerManutençao.getSelectedItem().toString();
        String ativo = spinnerAtivo.getSelectedItem().toString();
        String turma = spinnerTurma.getSelectedItem().toString();
        String equipamento = Equipamento.getText().toString();
        String lider = Lider.getText().toString();
        String data = Data.getText().toString();
        String Relatorio = relatorio.getText().toString();
        String Pendencia = pendendia.getText().toString();

      CadastraRelatoriosTurno cadastro = new CadastraRelatoriosTurno();
        cadastro.setIdUsuarios( Idusuariolougado );
        cadastro.setManuteçao( manutençao );
        cadastro.setAtivo(ativo);
        cadastro.setEquipamento(equipamento);
        cadastro.setLider(lider);
        cadastro.setData( data );
        cadastro.setTurma(turma);
        cadastro.setRelatorio(Relatorio);
        cadastro.setPendencia(Pendencia);
        return cadastro;

    }

    public void validarDadosAnuncio(View view){

        castro = configurarCadastro();


        if (!castro.getManuteçao().isEmpty() ){

            if (!castro.getAtivo().isEmpty() ){

                if (!castro.getEquipamento().isEmpty() ){

                    if (!castro.getLider().isEmpty() ){

                        if (!castro.getData().isEmpty() ){

                            if (!castro.getTurma().isEmpty() ){

                                if (!castro.getRelatorio().isEmpty() ){

                                    if (!castro.getPendencia().isEmpty() ){


                                        castro.salvar();

                                        Intent i = new Intent(Tela_de_Cadastra_Relatorios_Publicos.this, Tela_Lista_dos_Relatorio_Publicos.class);
                                        startActivity( i );
                                        finish();

                                    }else {
                                        exibirMensagemErro("Escreva Pendencia");
                                    }


                                }else {
                                    exibirMensagemErro("Escreva o Relatorio");
                                }


                            }else {
                                exibirMensagemErro("Selecione a turma");
                            }


                        }else {
                            exibirMensagemErro("Selecione a Data");
                        }


                    }else {
                        exibirMensagemErro("Selecione o Lider");
                    }


                }else {
                    exibirMensagemErro("Selecione tipo do Equipamento");
                }


            }else {
                exibirMensagemErro("Selecione tipo do ativo");
            }


        }else {
            exibirMensagemErro("Selecione tipo de manutençao");
        }

    }

    private void exibirMensagemErro(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v) {



    }

////////////////////////////////////////////////////////////////////////////////////////////////////


    public void InicializarComponestes(){


        spinnerManutençao = findViewById(R.id.spinnerManutençao);
        spinnerAtivo = findViewById(R.id.spinnerAtivo);
        spinnerTurma = findViewById(R.id.spinnerTurma);
        Equipamento = findViewById(R.id.Equipamentotext);
        Lider = findViewById(R.id.textoLider);
        Data = findViewById(R.id.textoDada);
        relatorio = findViewById(R.id.textoRelatorio);
        pendendia = findViewById(R.id.textoPendencia);



    }

    private void carregarDadosSpinner(){


        //Configura spinner de manutençao
        String[] manutençao = getResources().getStringArray(R.array.Manuteçao);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                manutençao
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
       spinnerManutençao.setAdapter( adapter );



        //Configura spinner de ativo
        String[] ativo = getResources().getStringArray(R.array.Ativo);
        ArrayAdapter<String> adapterAtivo = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                ativo
        );
        adapterAtivo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerAtivo.setAdapter( adapterAtivo );

        //Configura spinner de turma
        String[] turma = getResources().getStringArray(R.array.Turma);
        ArrayAdapter<String> adapterTurma = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                turma
        );
        adapterTurma.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerTurma.setAdapter( adapterTurma );


    }


}
