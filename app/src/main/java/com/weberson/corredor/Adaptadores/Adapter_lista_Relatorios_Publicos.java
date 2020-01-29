package com.weberson.corredor.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weberson.corredor.Class.CadastraRelatoriosTurno;
import com.weberson.corredor.R;

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
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adpter_lista2_relatorios, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        // we apply animation to views here
        // first lets create an animation for user photo
    //    myViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
     //   myViewHolder.container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));


        CadastraRelatoriosTurno cadastraRelatoriosTurno = relatoriosTurnos.get(i);
        myViewHolder.te.setText( cadastraRelatoriosTurno.getAtivo() );
        myViewHolder.ativo.setText( cadastraRelatoriosTurno.getEquipamento() );
        myViewHolder.relatorios.setText( cadastraRelatoriosTurno.getRelatorio() );
        myViewHolder.data.setText(cadastraRelatoriosTurno.getData());

    }

    @Override
    public int getItemCount() {
        return relatoriosTurnos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView te;
        TextView data;
        TextView ativo;
        TextView relatorios;
        RelativeLayout container;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            te = itemView.findViewById(R.id.id_TE);
            ativo = itemView.findViewById(R.id.text250status);
            relatorios = itemView.findViewById(R.id.relatorio_id);
            container = itemView.findViewById(R.id.container);
            data = itemView.findViewById(R.id.textparadostatus);
        }
    }
}
