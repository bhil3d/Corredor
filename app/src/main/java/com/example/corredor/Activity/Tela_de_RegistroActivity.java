package com.example.corredor.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Tela_de_RegistroActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha,componome,compomatricula,campogerencia,tipoUsuario;
    private Button botaoCadastrar;
    private ProgressBar progressBar;

    private CadastroDeUsuarios cadastro;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_de__registroactivity_);

        inicializarComponentes();

        //Cadastrar usuario
      //  progressBar.setVisibility(View.GONE);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textosenha = campoSenha.getText().toString();
                String textoNome = componome.getText().toString();
                String textoMatricula = compomatricula.getText().toString();
                String textoGerencia = campogerencia.getText().toString();
                String textoTipousuario = tipoUsuario.getText().toString();

               // if( !textoNome.isEmpty() ){
                    if( !textoEmail.isEmpty() ) {

                        if (!textoNome.isEmpty()){

                            if (!textoMatricula.isEmpty()){

                            if (!textosenha.isEmpty()) {

                                if (!textoGerencia.isEmpty()) {
                                    if (!textoTipousuario.isEmpty()) {

                                cadastro = new CadastroDeUsuarios();
                                // usuario.setNome( textoNome );
                                cadastro.setEmail(textoEmail);
                                cadastro.setSenha(textosenha);
                                cadastro.setNome(textoNome);
                                cadastro.setGerencia(textoGerencia);
                                cadastro.setMatricula(textoMatricula);
                                cadastro.setTipoUsuario(textoTipousuario);
                                cadastrar(cadastro);

                                    } else {
                                        Toast.makeText(Tela_de_RegistroActivity.this,
                                                "Preencha o tipo de usuario!", Toast.LENGTH_SHORT).show(); }

                                } else {
                                    Toast.makeText(Tela_de_RegistroActivity.this,
                                            "Preencha a senha!", Toast.LENGTH_SHORT).show(); }

                            } else {
                                Toast.makeText(Tela_de_RegistroActivity.this,
                                        "Preencha a senha!", Toast.LENGTH_SHORT).show(); }

                            } else {
                                Toast.makeText(Tela_de_RegistroActivity.this,
                                        "Preencha a matricula!", Toast.LENGTH_SHORT).show(); }

                        } else {
                            Toast.makeText(Tela_de_RegistroActivity.this,
                                    "Preencha o nome!", Toast.LENGTH_SHORT).show(); }



                    }else{
                        Toast.makeText(Tela_de_RegistroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show(); }


              //  }else{Toast.makeText(CadastroActivity.this,"Preencha o nome!",Toast.LENGTH_SHORT).show();}


            }
        });


    }

    public void cadastrar(final CadastroDeUsuarios usuario){

      //  progressBar.setVisibility(View.VISIBLE);
        autenticacao = ConfiguracaoFirebase2.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if( task.isSuccessful() ){

                            try {

                               // progressBar.setVisibility(View.GONE);

                                //Salvar dados no firebase
                                String idUsuario = task.getResult().getUser().getUid();
                                usuario.setIdUsuario( idUsuario );
                                usuario.salvar();

                                //Salvar dados no profile do Firebase
                                UsuarioFirebase.atualizarNomeUsuario( usuario.getNome() );
                                UsuarioFirebase.atualizarNomeUsuario( usuario.getMatricula() );
                                Toast.makeText(Tela_de_RegistroActivity.this,
                                        "Cadastro com sucesso",
                                        Toast.LENGTH_SHORT).show();

                                startActivity( new Intent(getApplicationContext(), Tela_Menu_Principal.class));
                                finish();

                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }else {

                          //  progressBar.setVisibility( View.GONE );

                            String erroExcecao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Por favor, digite um e-mail válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Este conta já foi cadastrada";
                            } catch (Exception e) {
                                erroExcecao = "ao cadastrar usuário: "  + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(Tela_de_RegistroActivity.this,
                                    "Erro: " + erroExcecao ,
                                    Toast.LENGTH_SHORT).show();


                        }

                    }
                }
        );

    }

    public void inicializarComponentes(){

        componome       = findViewById(R.id.editeNome);
        campoEmail      = findViewById(R.id.editeEmail);
        campoSenha      = findViewById(R.id.ediTsenha);
        compomatricula =findViewById(R.id.editematricula);
        campogerencia =findViewById(R.id.editegerencia);
        botaoCadastrar  = findViewById(R.id.butaocadastro);
        tipoUsuario = findViewById(R.id.editTipoUsuario);

       // progressBar     = findViewById(R.id.progressCadastro);

     //   campoNome.requestFocus();

    }


}
