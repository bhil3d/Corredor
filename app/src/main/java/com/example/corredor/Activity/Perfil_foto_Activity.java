package com.example.corredor.Activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Perfil_foto_Activity extends AppCompatActivity {

    private ImageView imgperfil;
    private CadastroDeUsuarios usuarioselecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_foto_);
        usuarioselecionado = UsuarioFirebase.getDadosUsuarioLogado();
        getSupportActionBar().setTitle( usuarioselecionado.getNome() );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imgperfil = findViewById(R.id.imgFoto_perfil);

        recuperarFotoUsuario();

    }

    private void recuperarFotoUsuario(){

        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        //Recuperar foto do usuário
        Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Perfil_foto_Activity.this)
                    .load( url )
                    .into( imgperfil );
        }



    }

}
