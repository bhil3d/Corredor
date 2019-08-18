package com.example.corredor.Class;

import com.example.corredor.Configuraçoes.ConfiguraçaosFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Cadastro_Status_De_Ativos implements Serializable {

    private String idStatus;
    private String ativo;
    private String equipamento;
    private String status;
    private String StatusAtivo;


    public Cadastro_Status_De_Ativos() {
        DatabaseReference relatorioRef = ConfiguraçaosFirebase.getFirebase()
                .child("meus relatorios");
        setIdStatus(relatorioRef.push().getKey());


    }

    public Cadastro_Status_De_Ativos(String ativos, String spinerativo, String spinerparado, String spinermanutecao,String idstatus) {
this.idStatus = idstatus;
this.ativo=spinerativo;
this.equipamento = ativos;
this.status=spinerparado;
this.StatusAtivo=spinermanutecao;

    }


    public void salvar(){
        DatabaseReference relatorioRef = ConfiguraçaosFirebase.getFirebase()
                .child("StatusEquipamentos");

        relatorioRef.child(getAtivo())
                .child(getStatus())
                .child(getIdStatus())
                .setValue(this);




    }

    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusAtivo() {
        return StatusAtivo;
    }

    public void setStatusAtivo(String statusAtivo) {
        StatusAtivo = statusAtivo;
    }
}
