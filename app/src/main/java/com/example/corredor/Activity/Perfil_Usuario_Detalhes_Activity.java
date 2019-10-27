package com.example.corredor.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Perfil_Usuario_Detalhes_Activity extends AppCompatActivity {

    private boolean isOpen = false ;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth autenticacao;
    private ConstraintSet layout1,layout2;
    private DatabaseReference relatoriosPublicosRef;
    private ConstraintLayout constraintLayout ;
    private ImageView imageViewPhoto,imagemfoto2;
    private TextView nomeUsuario,gerenciaUsuario,matriculaUsuario,emailUsuario,nomeUsuariofoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__usuario__detalhes_);


        // changing the status bar color to transparent
        //Configurações iniciais
        autenticacao = FirebaseAuth.getInstance();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        imageViewPhoto = findViewById(R.id.photo);
       imagemfoto2 = findViewById(R.id.cover);
        constraintLayout = findViewById(R.id.constraint_layout);
        layout1.clone(this,R.layout.profile_expanded);
        layout2.clone(constraintLayout);

        recuperarFotoUsuario();
        Recuperardadosdousuário();
        inicializarComponentes();

    //    idUsuarioLogado = ConfiguracaoFirebase2.getIdUsuario();
       // relatoriosPublicosRef = ConfiguracaoFirebase.getFirebase();

        //Configurações iniciais
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("usuarios")
                .child(ConfiguracaoFirebase2 .getIdUsuario() );



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mudarposicaoDolayouty();
            }
        }, 2000);

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen ;
                }

                else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen ;

                }






            }
        });

    }

    private void inicializarComponentes() {

        nomeUsuario      = findViewById(R.id.nameperfiluser);
        nomeUsuariofoto = findViewById(R.id.nomeperfilfoto);
        gerenciaUsuario        = findViewById(R.id.gerenciaperfiluser);
        matriculaUsuario         = findViewById(R.id.matriculaperfiluser);
        emailUsuario        = findViewById(R.id.emailperfiluser);



    }

    private void mudarposicaoDolayouty() {
        TransitionManager.beginDelayedTransition(constraintLayout);
        layout1.applyTo(constraintLayout);
        isOpen = !isOpen ;

    }

    private void recuperarFotoUsuario() {
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        //Recuperar foto do usuário
        Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Perfil_Usuario_Detalhes_Activity.this)
                    .load( url )
                    .into( imageViewPhoto );
        }

        if( url != null ){
            Glide.with(Perfil_Usuario_Detalhes_Activity.this)
                    .load( url )
                    .into( imagemfoto2 );
        }




    }

    private void Recuperardadosdousuário(){

        //Configurações iniciais
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("usuarios")
                .child(ConfiguracaoFirebase2 .getIdUsuario() );

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    CadastroDeUsuarios empresa = dataSnapshot.getValue(CadastroDeUsuarios.class);
                    nomeUsuario.setText(empresa.getNome());
                    gerenciaUsuario.setText(empresa.getGerencia());
                    matriculaUsuario.setText(empresa.getMatricula());
                    emailUsuario.setText(empresa.getEmail());
                    nomeUsuariofoto.setText(empresa.getNome());




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public  void abrifoto(View view){

        Intent intent = new Intent(Perfil_Usuario_Detalhes_Activity.this,Perfil_foto_Activity.class);
        startActivity(intent);
        finish();

    }

}
