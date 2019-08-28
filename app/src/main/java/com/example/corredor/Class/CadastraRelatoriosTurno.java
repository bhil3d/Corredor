package com.example.corredor.Class;



import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import static com.example.corredor.Configuraçoes.ConfiguracaoFirebase2.getIdUsuario;

public class CadastraRelatoriosTurno implements Serializable {
    private String idUsuarios;
    private String idRelatorio;
    private String manutecao;
    private String ativo;
    private String equipamento;
    private String lider;
    private String data;
    private String turma;
    private String relatorio;
    private String pendencia;



    public CadastraRelatoriosTurno() {
        DatabaseReference relatorioRef = ConfiguracaoFirebase.getFirebase()
        .child("meus relatorios");
        setIdRelatorio(relatorioRef.push().getKey());


    }

    public CadastraRelatoriosTurno
            (String idUsuarios, String idRelatorio,
             String manuteçao, String ativo, String equipamento, String lider,
             String data, String turma, String relatorio, String pendencia) {
        this.idUsuarios = idUsuarios;
        this.idRelatorio = idRelatorio;
        this.manutecao = manuteçao;
        this.ativo = ativo;
        this.equipamento = equipamento;
        this.lider = lider;
        this.data = data;
        this.turma = turma;
        this.relatorio = relatorio;
        this.pendencia = pendencia;
    }

    public CadastraRelatoriosTurno
            (String manuteA, String ativoD,
             String dataE, String liderId, String turmaL, String relatorioM,
             String pendencia, String id_relatorioRL, String equipamentoT) {
        this.idRelatorio = liderId;
        this.manutecao = relatorioM;
        this.ativo = manuteA;
        this.lider = turmaL;
        this.equipamento=dataE;
        this.data = ativoD;
        this.turma = equipamentoT;
        this.relatorio = id_relatorioRL;
        this.pendencia = pendencia;
    }


    public void salvar(){
    DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    DatabaseReference produtoRef = firebaseRef
            .child("meus relatorios")

  .child( getIdUsuario() )
            .child( getIdRelatorio() );
    produtoRef.setValue(this);
    salvarRelatoriosPublicos();
}

public void salvarRelatoriosPublicos(){
        DatabaseReference relatorioRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorios");

        relatorioRef.child(getManuteçao())
                .child(getAtivo())
                .child(getIdRelatorio())
                .setValue(this);




}


    public void remover(){

        String idUsuario = getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("meus relatorios")
                .child( idUsuario )
                .child( getIdRelatorio() );

        anuncioRef.removeValue();


    }

    public String getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(String idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getIdRelatorio() { return idRelatorio; }

    public void setIdRelatorio(String idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getManuteçao() {
        return manutecao;
    }

    public void setManuteçao(String manuteçao) {
        this.manutecao = manuteçao;
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

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public String getPendencia() {
        return pendencia;
    }

    public void setPendencia(String pendencia) {
        this.pendencia = pendencia;
    }
}
