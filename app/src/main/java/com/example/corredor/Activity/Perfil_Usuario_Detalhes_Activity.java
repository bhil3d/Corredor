package com.example.corredor.Activity;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.R;
import com.google.firebase.auth.FirebaseUser;

public class Perfil_Usuario_Detalhes_Activity extends AppCompatActivity {

    private boolean isOpen = false ;
    private CadastroDeUsuarios usuarioselecionado;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;
    private ImageView imageViewPhoto,imagemfoto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__usuario__detalhes_);

        // changing the status bar color to transparent

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        imageViewPhoto = findViewById(R.id.photo);
        imagemfoto2 = findViewById(R.id.cover);
        constraintLayout = findViewById(R.id.constraint_layout);
        layout2.clone(this,R.layout.profile_expanded);
        layout1.clone(constraintLayout);

        recuperarFotoUsuario();

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen ;
                }

                else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen ;

                }






            }
        });

    }

    private void recuperarFotoUsuario() {
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        //Recuperar foto do usuário
        Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Perfil_Usuario_Detalhes_Activity.this)
                    .load( url )
                    .into( imagemfoto2 );
        }
        if( url != null ){
            Glide.with(Perfil_Usuario_Detalhes_Activity.this)
                    .load( url )
                    .into( imageViewPhoto );
        }
    }
}
