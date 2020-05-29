package com.weberson.corredor.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class Funcionario implements Parcelable {

    private String nome;
    private String idUsuario;
    private String email;
    private String senha;
    private String matricula;
    private String gerencia;
    private String tipoUsuario;
    private String caminhoFoto;




    public Funcionario(){


    }



    public Funcionario(String nome, String email, String caminhoFoto) {
        this.nome = nome;
        this.email = email;
        this.caminhoFoto = caminhoFoto;

    }

    public Funcionario(String nome, String caminhoFoto) {
        this.nome = nome;
        this.caminhoFoto = caminhoFoto;

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idUsuario);
        dest.writeString(this.nome);
        dest.writeString(this.caminhoFoto);
        dest.writeString(this.email);
        dest.writeString(this.senha);
        dest.writeString(this.matricula);
        dest.writeString(this.gerencia);
        dest.writeString(this.tipoUsuario);

    }

    protected Funcionario(Parcel in) {
        this.idUsuario = in.readString();
        this.nome = in.readString();
        this.caminhoFoto = in.readString();
        this.email = in.readString();
        this.senha = in.readString();
        this.matricula = in.readString();
        this.gerencia = in.readString();
        this.tipoUsuario = in.readString();

    }

    public static final Creator<Funcionario> CREATOR = new Creator<Funcionario>() {
        @Override
        public Funcionario createFromParcel(Parcel source) {
            return new Funcionario(source);
        }

        @Override
        public Funcionario[] newArray(int size) {
            return new Funcionario[size];
        }
    };
}
