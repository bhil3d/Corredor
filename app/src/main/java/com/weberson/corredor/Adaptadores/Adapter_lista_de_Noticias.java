package com.weberson.corredor.Adaptadores;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.weberson.corredor.Class.CadastroNoticias;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_lista_de_Noticias extends RecyclerView.Adapter<Adapter_lista_de_Noticias.MyViewHolder> {

private List<CadastroNoticias> listadenoticias;
private Context context;

    public Adapter_lista_de_Noticias(List<CadastroNoticias> listadenoticias, Context context) {
        this.listadenoticias = listadenoticias;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adaptadornoticia, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        CadastroNoticias paginadenoticias = listadenoticias.get(i);
        myViewHolder.Titulo.setText( paginadenoticias.getTitulo() );
        myViewHolder.Conteudo.setText( paginadenoticias.getConteudo() );
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        if (paginadenoticias.getUrlimagem() != null){

            Uri uri = Uri.parse(paginadenoticias.getUrlimagem());
            Glide.with(context).load(uri).into(myViewHolder.Urimagem);

        }else {
            myViewHolder.Urimagem.setImageResource(R.drawable.ic_menu_gallery);

        }


        //Recuperar foto do usuário
        Uri url = usuarioPerfil.getPhotoUrl();

            Glide.with(context)
                    .load( url )
                    .into( myViewHolder.imagemcirculo );





    }

    @Override
    public int getItemCount() {
        return listadenoticias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo;
        TextView Conteudo;
        ImageView Urimagem;
CircleImageView imagemcirculo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = itemView.findViewById(R.id.idtituloNoticias);
            Conteudo = itemView.findViewById(R.id.idconteudonoticias);
            Urimagem = itemView.findViewById(R.id.idimagemNoticias);
            imagemcirculo = itemView.findViewById(R.id.imagemperfilnoticias);

        }
    }
}
