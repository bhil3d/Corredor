package com.example.corredor.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Tela_de_LougarActivity extends AppCompatActivity {

    private Button lougar;
    private EditText email;
    private EditText senha;
    private FirebaseAuth autenticacao;
    private CadastroDeUsuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_de__lougaractivity_);

        autenticacao = ConfiguracaoFirebase2.getFirebaseAutenticacao();

        verificarUsuarioLogado();

        inicializaComponentes();


        //Fazer login do usuario

       // progressBar.setVisibility( View.GONE );

        lougar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = email.getText().toString();
                String textosenha = senha.getText().toString();

                if( !textoEmail.isEmpty() ){
                    if( !textosenha.isEmpty() ){

                        usuario = new CadastroDeUsuarios();
                        usuario.setEmail( textoEmail );
                        usuario.setSenha( textosenha );
                        validarLogin( usuario );

                    }else{
                        Toast.makeText(Tela_de_LougarActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Tela_de_LougarActivity.this,
                            "Preencha o e-mail!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase2.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null ){
            startActivity(new Intent(getApplicationContext(), Tela_Menu_Principal.class));
            finish();
        }
    }

    public void validarLogin( CadastroDeUsuarios usuario ){

     //   progressBar.setVisibility( View.VISIBLE );
        autenticacao = ConfiguracaoFirebase2.getFirebaseAutenticacao();

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){

                    Toast.makeText(Tela_de_LougarActivity.this,
                            "Logado com sucesso",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Tela_Menu_Principal.class));
                    finish();

                }else {
                    Toast.makeText(Tela_de_LougarActivity.this,
                            "Erro ao fazer login : " + task.getException() ,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void inicializaComponentes(){
        email = findViewById(R.id.editetnome);
        senha = findViewById(R.id.editeSenha);
        lougar = findViewById(R.id.butaolougar);
      // tipoAcesso = findViewById(R.id.switchAcesso);
    }

    public void abrirCadastro(View view){
        Intent i = new Intent(Tela_de_LougarActivity.this, Tela_de_RegistroActivity.class);
        startActivity( i );
    }


}
