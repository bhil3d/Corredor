package com.example.corredor.Class;

public class cadastroR {

    private String idUsuarios;
    private String idRelatorio;
    private String manuteçao;
    private String ativo;
    private String equipamento;
    private String lider;
    private String data;
    private String turma;
    private String relatorio;
    private String pendencia;

    public cadastroR() {


    }

    public cadastroR(String ativo, String data, String equipamento, String lider, String manutencao, String pendencias, String relatorios, String turma, String idrelatorios) {

    }


    public String getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(String idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(String idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getManuteçao() {
        return manuteçao;
    }

    public void setManuteçao(String manuteçao) {
        this.manuteçao = manuteçao;
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
