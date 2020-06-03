package com.weberson.corredor.Class;

import java.io.Serializable;

public class CadastroNoticias implements Serializable {
    private String urlimagem;
    private String titulo;
    private String subtitulo;
    private String conteudo;


    public CadastroNoticias() {

    }

    public CadastroNoticias ( String conteudo, String titulo, String urlimagem){
        this.urlimagem = urlimagem;
        this.titulo = titulo;
        this.conteudo = conteudo;


    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getUrlimagem() {
        return urlimagem;
    }

    public void setUrlimagem(String urlimagem) {
        this.urlimagem = urlimagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
