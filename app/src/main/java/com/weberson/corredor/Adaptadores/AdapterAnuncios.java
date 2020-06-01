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
import com.weberson.corredor.Class.CadastroNoticias;
import com.weberson.corredor.R;

import java.util.List;

/**
 * Created by jamiltondamasceno
 */

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.MyViewHolder> {

    private List<CadastroNoticias> anuncios;
    private Context context;

    public AdapterAnuncios(List<CadastroNoticias> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptadornoticias, parent, false);
        return new MyViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CadastroNoticias anuncio = anuncios.get(position);
        holder.Titulo.setText( anuncio.getTitulo() );
        holder.Conteudo.setText( anuncio.getConteudo() );

        //Pega a primeira imagem da lista
        Uri uri = Uri.parse(anuncio.getUrlimagem());
        Glide.with(context).load(uri).into(holder.Urimagem);

    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Titulo;
        TextView Conteudo;
        ImageView Urimagem;

        public MyViewHolder(View itemView) {
            super(itemView);

            Titulo = itemView.findViewById(R.id.idtituloNoticias);
            Conteudo = itemView.findViewById(R.id.idconteudonoticias);
            Urimagem = itemView.findViewById(R.id.idimagemNoticias);

        }
    }

}
