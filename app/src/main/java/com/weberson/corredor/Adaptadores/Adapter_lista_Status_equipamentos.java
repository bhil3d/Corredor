package com.weberson.corredor.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weberson.corredor.Class.Cadastro_Status_De_Ativos;
import com.weberson.corredor.R;

import java.util.List;

public class Adapter_lista_Status_equipamentos extends RecyclerView.Adapter<Adapter_lista_Status_equipamentos.MyViewHolder> {

private List<Cadastro_Status_De_Ativos> statusequipamentos;
private Context context;

    public Adapter_lista_Status_equipamentos(List<Cadastro_Status_De_Ativos> statusequipamentos, Context context) {
        this.statusequipamentos = statusequipamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adaptador_status_de_equipamentos, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Cadastro_Status_De_Ativos StatusEquipamentos = statusequipamentos.get(i);
        myViewHolder.TE.setText( StatusEquipamentos.getAtivo() );
        myViewHolder.ativo.setText( StatusEquipamentos.getEquipamento() );
        myViewHolder.parado.setText( StatusEquipamentos.getStatus() );
        myViewHolder.statusmanutencao.setText( StatusEquipamentos.getStatusAtivo() );

    }

    @Override
    public int getItemCount() {
        return statusequipamentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TE;
        TextView ativo;
        TextView parado;
       TextView statusmanutencao;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TE = itemView.findViewById(R.id.textTEstatus);
            ativo = itemView.findViewById(R.id.text250status);
            parado = itemView.findViewById(R.id.textparadostatus);
            statusmanutencao = itemView.findViewById(R.id.relatorio_id);
        }
    }
}
