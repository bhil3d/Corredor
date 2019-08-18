package com.example.corredor.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.corredor.Class.CadastraRelatoriosTurno;
import com.example.corredor.R;

import java.util.List;

public class Adapter_lista_Relatorios_Publicos extends RecyclerView.Adapter<Adapter_lista_Relatorios_Publicos.MyViewHolder> {

private List<CadastraRelatoriosTurno> relatoriosTurnos;
private Context context;

    public Adapter_lista_Relatorios_Publicos(List<CadastraRelatoriosTurno> relatoriosTurnos, Context context) {
        this.relatoriosTurnos = relatoriosTurnos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_lista_de_relatorios_publicos, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        CadastraRelatoriosTurno cadastraRelatoriosTurno = relatoriosTurnos.get(i);
        myViewHolder.te.setText( cadastraRelatoriosTurno.getAtivo() );
        myViewHolder.ativo.setText( cadastraRelatoriosTurno.getEquipamento() );
        myViewHolder.relatorios.setText( cadastraRelatoriosTurno.getRelatorio() );

    }

    @Override
    public int getItemCount() {
        return relatoriosTurnos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView te;
        TextView ativo;
        TextView relatorios;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            te = itemView.findViewById(R.id.te_id_text);
            ativo = itemView.findViewById(R.id.ativo_id);
            relatorios = itemView.findViewById(R.id.relatorio_id);
        }
    }
}
