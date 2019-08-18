package com.example.corredor.Class;

import com.example.corredor.Configuraçoes.ConfiguraçaosFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class CadastroDeUsuarios implements Serializable {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String matricula;
    private String gerencia;

    public CadastroDeUsuarios() {

    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguraçaosFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child( getIdUsuario() );
        usuariosRef.setValue( this );
    }


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}



