package com.weberson.corredor.Class;

import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Cadastro_Status_De_Ativos implements Serializable {

    private String idStatus;
    private String ativo;
    private String equipamento;
    private String status;
    private String StatusAtivo;


    public Cadastro_Status_De_Ativos() {
        DatabaseReference relatorioRef = ConfiguracaoFirebase2.getFirebase()
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
        DatabaseReference relatorioRef = ConfiguracaoFirebase2.getFirebase()
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
