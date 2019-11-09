package com.weberson.corredor.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weberson.corredor.Class.ClassDF;
import com.weberson.corredor.R;

import java.util.List;

public class Adapter_lista_DF extends RecyclerView.Adapter<Adapter_lista_DF.MyViewHolder> {

private List<ClassDF> statusequipamentos;
private Context context;

    public Adapter_lista_DF(List<ClassDF> statusequipamentos, Context context) {
        this.statusequipamentos = statusequipamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adaptador_df, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ClassDF StatusEquipamentos = statusequipamentos.get(i);
        myViewHolder.Dvdia.setText( StatusEquipamentos.getSpd11dia() );
        myViewHolder.Dvsemana.setText( StatusEquipamentos.getSpd11semana() );
        myViewHolder.Dvmes.setText( StatusEquipamentos.getSd11mes() );
        myViewHolder.Dvano.setText( StatusEquipamentos.getSpd11ano() );
        myViewHolder.Pvdia.setText( StatusEquipamentos.getSppatroldia() );
        myViewHolder.Pvsemana.setText( StatusEquipamentos.getSppatrolsemana() );
        myViewHolder.Pvmes.setText( StatusEquipamentos.getSppatrolmes() );
        myViewHolder.Pvano.setText( StatusEquipamentos.getSppatrolano() );

        myViewHolder.Pfvdia.setText( StatusEquipamentos.getSpperfuratrizdia() );
        myViewHolder.Pfvsemana.setText( StatusEquipamentos.getSpperfuratrizsemana() );
        myViewHolder.Pfvmes.setText( StatusEquipamentos.getSpperfuratrizmes() );
        myViewHolder.Pfvano.setText( StatusEquipamentos.getSpperfuratrizano() );

        myViewHolder.Rvdia.setText( StatusEquipamentos.getSpretrodia() );
        myViewHolder.Rvsemana.setText( StatusEquipamentos.getSpretrosemana() );
        myViewHolder.Rvmes.setText( StatusEquipamentos.getSpretromes() );
        myViewHolder.Rvano.setText( StatusEquipamentos.getSpretroano() );

    }

    @Override
    public int getItemCount() {
        return statusequipamentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Dvdia;
        TextView Dvsemana;
        TextView Dvmes;
        TextView Dvano;
        TextView Pvdia;
        TextView Pvsemana;
        TextView Pvmes;
        TextView Pvano;

        TextView Rvdia;
        TextView Rvsemana;
        TextView Rvmes;
        TextView Rvano;

        TextView Pfvdia;
        TextView Pfvsemana;
        TextView Pfvmes;
        TextView Pfvano;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Dvdia = itemView.findViewById(R.id.Dvdia);
            Dvsemana = itemView.findViewById(R.id.Dvsemana);
            Dvmes = itemView.findViewById(R.id.Dvmes);
            Dvano = itemView.findViewById(R.id.Dvano);
            Pvdia = itemView.findViewById(R.id.Pvdia);
            Pvsemana = itemView.findViewById(R.id.Pvsemana);
            Pvmes = itemView.findViewById(R.id.Pvmes);
            Pvano = itemView.findViewById(R.id.Pvano);
            Rvdia = itemView.findViewById(R.id.Rvdia);
            Rvsemana = itemView.findViewById(R.id.Rvsemana);
            Rvmes = itemView.findViewById(R.id.Rvmes);
            Rvano = itemView.findViewById(R.id.Rvano);
            Pfvdia = itemView.findViewById(R.id.Pfvdia);
            Pfvsemana = itemView.findViewById(R.id.Pfvsemana);
            Pfvmes = itemView.findViewById(R.id.pfvmes);
            Pfvano = itemView.findViewById(R.id.Pfvano);
        }
    }
}
