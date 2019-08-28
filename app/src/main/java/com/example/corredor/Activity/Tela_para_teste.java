package com.example.corredor.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.R;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;


public class Tela_para_teste extends AppCompatActivity implements View.OnClickListener {

private CircleImageView imgperfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_para_teste);

        imgperfil = findViewById(R.id.IdimgMenuteste);
        recuperarFotoUsuario();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


        }

    }


    private void recuperarFotoUsuario(){

        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        //Recuperar foto do usuário
        Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Tela_para_teste.this)
                    .load( url )
                    .into( imgperfil );
        }



    }



}
