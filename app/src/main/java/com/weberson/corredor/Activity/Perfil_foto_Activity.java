package com.weberson.corredor.Activity;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weberson.corredor.Class.CadastroDeUsuarios;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.R;
import com.google.firebase.auth.FirebaseUser;

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
