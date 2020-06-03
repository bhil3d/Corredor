package com.weberson.corredor.Activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.weberson.corredor.Class.CadastroNoticias;
import com.weberson.corredor.R;

public class ActivitypaginaNoticias extends AppCompatActivity {

    private ImageView imagemcapa;
    private TextView titulo,subititulo,conteudo;
    private CadastroNoticias noticiaSelecionada;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitypagina_noticias);

        //configura Toolbar
     //   getActionBar().setTitle("Informativos");

        inicializarComponestes();

        noticiaSelecionada = (CadastroNoticias)getIntent().getSerializableExtra("anuncioSelecionado");
        if (noticiaSelecionada !=null){

            titulo.setText(noticiaSelecionada.getTitulo());
            conteudo.setText(noticiaSelecionada.getConteudo());


            if (noticiaSelecionada.getUrlimagem() != null){

                Uri uri = Uri.parse(noticiaSelecionada.getUrlimagem());
                Glide.with(getBaseContext()).load(uri).into(imagemcapa);

            }






        }

    }



    private void inicializarComponestes() {
        imagemcapa = findViewById(R.id.fotocapaNoticias);
        titulo = findViewById(R.id.textitulo);
        subititulo=findViewById(R.id.textsubititulo);
        conteudo=findViewById(R.id.textoNoticias);
    }


}
