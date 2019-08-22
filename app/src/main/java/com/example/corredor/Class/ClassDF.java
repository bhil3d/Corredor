package com.example.corredor.Class;

import java.io.Serializable;

public class ClassDF implements Serializable {

    private String IdDf;
    private String spd11dia;
    private String spd11semana;
    private String sd11mes;
    private String spd11ano;
    private String spretrodia;
    private String spretrosemana;
    private String spretromes;
    private String spretroano;
    private String sppatroldia;
    private String sppatrolsemana;
    private String sppatrolmes;
    private String sppatrolano;
    private String spperfuratrizdia;
    private String spperfuratrizsemana;
    private String spperfuratrizmes;
    private String spperfuratrizano;


    public ClassDF() {

    }

    public ClassDF(String ddia, String dsemana, String dmes, String dano,
                   String d1dia, String d1semana, String d1mes, String d1ano,
                   String d2dia, String d2semana, String d2mes, String d2ano,
                   String d3dia, String d3semana, String d3mes, String d3ano) {

        this.spd11dia = ddia;
        this.spd11semana = dsemana;
        this.sd11mes = dmes;
        this.spd11ano = dano;
        this.spretrodia = d1dia;
        this.spretrosemana = d1semana;
        this.spretromes = d1mes;
        this.spretroano = d1ano;
        this.sppatroldia = d2dia;
        this.sppatrolsemana = d2semana;
        this.sppatrolmes = d2mes;
        this.sppatrolano = d2ano;
        this.spperfuratrizdia = d3dia;
        this.spperfuratrizsemana = d3semana;
        this.spperfuratrizmes = d3mes;
        this.spperfuratrizano = d3ano;

    }


    public String getIdDf() {
        return IdDf;
    }

    public void setIdDf(String idDf) {
        IdDf = idDf;
    }

    public String getSpd11dia() {
        return spd11dia;
    }

    public void setSpd11dia(String spd11dia) {
        this.spd11dia = spd11dia;
    }

    public String getSpd11semana() {
        return spd11semana;
    }

    public void setSpd11semana(String spd11semana) {
        this.spd11semana = spd11semana;
    }

    public String getSd11mes() {
        return sd11mes;
    }

    public void setSd11mes(String sd11mes) {
        this.sd11mes = sd11mes;
    }

    public String getSpd11ano() {
        return spd11ano;
    }

    public void setSpd11ano(String spd11ano) {
        this.spd11ano = spd11ano;
    }

    public String getSpretrodia() {
        return spretrodia;
    }

    public void setSpretrodia(String spretrodia) {
        this.spretrodia = spretrodia;
    }

    public String getSpretrosemana() {
        return spretrosemana;
    }

    public void setSpretrosemana(String spretrosemana) {
        this.spretrosemana = spretrosemana;
    }

    public String getSpretromes() {
        return spretromes;
    }

    public void setSpretromes(String spretromes) {
        this.spretromes = spretromes;
    }

    public String getSpretroano() {
        return spretroano;
    }

    public void setSpretroano(String spretroano) {
        this.spretroano = spretroano;
    }

    public String getSppatroldia() {
        return sppatroldia;
    }

    public void setSppatroldia(String sppatroldia) {
        this.sppatroldia = sppatroldia;
    }

    public String getSppatrolsemana() {
        return sppatrolsemana;
    }

    public void setSppatrolsemana(String sppatrolsemana) {
        this.sppatrolsemana = sppatrolsemana;
    }

    public String getSppatrolmes() {
        return sppatrolmes;
    }

    public void setSppatrolmes(String sppatrolmes) {
        this.sppatrolmes = sppatrolmes;
    }

    public String getSppatrolano() {
        return sppatrolano;
    }

    public void setSppatrolano(String sppatrolano) {
        this.sppatrolano = sppatrolano;
    }

    public String getSpperfuratrizdia() {
        return spperfuratrizdia;
    }

    public void setSpperfuratrizdia(String spperfuratrizdia) {
        this.spperfuratrizdia = spperfuratrizdia;
    }

    public String getSpperfuratrizsemana() {
        return spperfuratrizsemana;
    }

    public void setSpperfuratrizsemana(String spperfuratrizsemana) {
        this.spperfuratrizsemana = spperfuratrizsemana;
    }

    public String getSpperfuratrizmes() {
        return spperfuratrizmes;
    }

    public void setSpperfuratrizmes(String spperfuratrizmes) {
        this.spperfuratrizmes = spperfuratrizmes;
    }

    public String getSpperfuratrizano() {
        return spperfuratrizano;
    }

    public void setSpperfuratrizano(String spperfuratrizano) {
        this.spperfuratrizano = spperfuratrizano;
    }
}
