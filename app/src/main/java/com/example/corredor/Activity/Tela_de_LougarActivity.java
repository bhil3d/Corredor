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
import com.example.corredor.Class.Util2;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Tela_de_LougarActivity extends AppCompatActivity {

    private Button lougar,btnFacebookLoug;
    private EditText email;
    private EditText senha;
    private FirebaseAuth autenticacao;
    private FirebaseUser user;
    private CadastroDeUsuarios usuario;
    private CallbackManager callbackManager;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_de__lougaractivity_);

        autenticacao = ConfiguracaoFirebase2.getFirebaseAutenticacao();
        autenticacao = FirebaseAuth.getInstance();

        servicosAutenticacao();
        verificarUsuarioLogado();
        inicializaComponentes();
        servicosFacebook();

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

btnFacebookLoug.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           signInFacebook();


                                       }
                                   }

);



    }


    private void servicosAutenticacao(){


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user!=null){

                    Toast.makeText(getBaseContext(),"Usuario "+ user.getEmail() + " está logado" , Toast.LENGTH_LONG).show();

                }else{


                }


            }
        };



    }


    //-------------------------------------------------SERVICOS LOGIN--------------------------------------------------

    private void servicosFacebook(){


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                adicionarContaFacebookaoFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(),"Cancelado" , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                String resultado = error.getMessage();

                Util2.opcoesErro(getBaseContext(),resultado);

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


    private void signInFacebook(){

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));


    }

    private void adicionarContaFacebookaoFirebase(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(getBaseContext(),Tela_Menu_Principal.class));


                        } else {

                            String resultado = task.getException().toString();

                            Util2.opcoesErro(getBaseContext(),resultado);
                        }

                        // ...
                    }
                });
    }



    private void inicializaComponentes(){
        email = findViewById(R.id.editetnome);
        senha = findViewById(R.id.editeSenha);
        lougar = findViewById(R.id.butaolougar);
        btnFacebookLoug = (Button)findViewById(R.id.btnFacebookLoug);

    }

    public void abrirCadastro(View view){
        Intent i = new Intent(Tela_de_LougarActivity.this, Tela_de_RegistroActivity.class);
        startActivity( i );
    }

    @Override
    protected void onStart() {
        super.onStart();

        autenticacao.addAuthStateListener(authStateListener);

    }






    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null){

            autenticacao.removeAuthStateListener(authStateListener);
        }

    }


}
