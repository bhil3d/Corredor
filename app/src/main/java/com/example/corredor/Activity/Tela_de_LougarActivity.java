package com.example.corredor.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.Util2;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class Tela_de_LougarActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginButton facebookLoginButton;
    private Button logGoogle;
    private EditText email;
    private EditText senha;
    private Button lougar;
    private FirebaseAuth autenticacao;
    private CadastroDeUsuarios usuario;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_de__lougaractivity_);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        ////facebook/////
        inicializarComponentes();
        inicializarFirebaseCallback();
        clicbtn();

/////////////////...email e senha...///////////////////////////
        servicosAutenticacao();
        verificarUsuarioLogado();
        inicializaComponentes2();

//////////    Google    //////////////////////////////////
        servicosGoogle();






        lougar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String textoEmail = email.getText().toString();
                String textosenha = senha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textosenha.isEmpty()) {

                        usuario = new CadastroDeUsuarios();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textosenha);
                        validarLogin(usuario);

                    } else {
                        Toast.makeText(Tela_de_LougarActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Tela_de_LougarActivity.this,
                            "Preencha o e-mail!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });






    }

    /////////////metodo de click////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.login_google:

                signInGoogle();

                break;
        }

    }


/////////////////////////...Google.....////////////////////////////////////////////////////////////////
    private void servicosGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInGoogle(){

GoogleSignInAccount accont = GoogleSignIn.getLastSignedInAccount(this);

if(accont ==null){

    Intent intent = googleSignInClient.getSignInIntent();


    startActivityForResult(intent,555);

}else{

    //já existe alem conectado pelo google
    Toast.makeText(getBaseContext(),"Já logado",Toast.LENGTH_LONG).show();
    startActivity(new Intent(getBaseContext(),Tela_Menu_Principal.class));

    //  googleSignInClient.signOut();
}

    }

    private void adicionarContaGoogleaoFirebase(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

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


/* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 555){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);

                adicionarContaGoogleaoFirebase(account);


            }catch (ApiException e){

                String resultado = e.getMessage();

                Util2.opcoesErro(getBaseContext(),resultado);
            }



        }

    }

*/
    ///////////////////////////////////FACEBOOK/////////////////////////////////////////////////////////////


    private void clicbtn() {
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaselogi(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

                alert("Operaçao cancelada");

            }

            @Override
            public void onError(FacebookException error) {

                alert("erro no login com Facebook");
            }
        });


    }

    private void firebaselogi(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Intent intent = new Intent(Tela_de_LougarActivity.this,Tela_Menu_Principal.class);
                            startActivity(intent);

                            alert("sucesso ao fazer lougin");

                        }else {

                            alert("erro com autenticaçao com firebase");

                        }

                    }
                });


    }

    private  void  alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();

    }

    private void inicializarFirebaseCallback() {
        autenticacao = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }

    private void inicializarComponentes() {
        facebookLoginButton = (LoginButton) findViewById(R.id.login_button);
        facebookLoginButton.setReadPermissions("email","public_profile");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }








    /////////////////////////////////////////FACEBOOK/////////////////////////////////////////////////////////////


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


    private void inicializaComponentes2(){
        email = findViewById(R.id.editetnome);
        senha = findViewById(R.id.editeSenha);
        lougar = findViewById(R.id.butaolougar);
        logGoogle = (Button) findViewById(R.id.login_google);
        logGoogle.setOnClickListener(this);





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
