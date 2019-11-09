package com.weberson.corredor.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weberson.corredor.Class.TelefonesUteis;
import com.weberson.corredor.R;

import java.util.List;

public class AdapterTelefones extends RecyclerView.Adapter<AdapterTelefones.MyViewHolder> {

    private List<TelefonesUteis> telefonesUteis;
    private Context context;

    public AdapterTelefones(List<TelefonesUteis> telefonesUteis, Context c) {
        this.telefonesUteis = telefonesUteis;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_lista_telefones, viewGroup, false);
        return new MyViewHolder(item);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        TelefonesUteis telefones = telefonesUteis.get(i);
        myViewHolder.telefones.setText( telefones.getTelefones() );

    }

    @Override
    public int getItemCount() {
        return telefonesUteis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView telefones;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            telefones = itemView.findViewById(R.id.telefonesid);

        }
    }
}
