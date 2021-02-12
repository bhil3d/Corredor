package com.weberson.corredor.Activity;

import android.app.DatePickerDialog;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weberson.corredor.Class.CadastraRelatoriosTurno;
import com.weberson.corredor.Class.DatePickerFragment;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.R;

import java.text.DateFormat;
import java.util.Calendar;

public class Tela_de_Cadastra_Relatorios_Publicos extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {


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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button button = (Button) findViewById(R.id.buttonDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        getSupportActionBar().setTitle("Novo relatorio");

        InicializarComponestes();
        carregarDadosSpinner();
        Idusuariolougado = UsuarioFirebase.getIdentificadorUsuario();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_salvar,menu);

        return super.onCreateOptionsMenu(menu);
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

    public void validarDadosAnuncio(){

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

                                /*        Intent i = new Intent(Tela_de_Cadastra_Relatorios_Publicos.this, Tela_Lista_dos_Relatorio_Publicos.class);
                                        startActivity( i );
                                        finish();

                                 */

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
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()){


            case R.id.item_salvar:
                validarDadosAnuncio();
                return true;


        }

        return super.onOptionsItemSelected(item);
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
        Data.setFocusable(false);



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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textoDada);
        textView.setText(currentDateString);

    }
}
