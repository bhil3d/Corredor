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

    public ClassDF(String ddia16, String Idtexto, String Dmes03, String Dsemana02,
                   String Ddia01, String Dano04, String d1mes05, String d1ano06,
                   String d2dia07, String d2semana08, String d2mes09, String d2ano10,
                   String d3dia11, String d3semana12, String d3mes13, String d3ano14,String D5id) {

        this.spd11dia = Ddia01;
        this.spd11semana = Dsemana02;
        this.sd11mes = Dmes03;
        this.spd11ano = Dano04;
        this.spretrodia = d2mes09;
        this.spretrosemana = d2ano10;
        this.spretromes = d3dia11;
        this.spretroano = d3semana12;
        this.sppatroldia = d1mes05;
        this.sppatrolsemana = d1ano06;
        this.sppatrolmes = d2dia07;
        this.sppatrolano = d2semana08;
        this.spperfuratrizdia = d3mes13;
        this.spperfuratrizsemana = d3ano14;
        this.spperfuratrizmes = D5id;
        this.spperfuratrizano = ddia16;
        this.IdDf = Idtexto;


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
