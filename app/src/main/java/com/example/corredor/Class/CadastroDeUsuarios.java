package com.example.corredor.Class;

import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CadastroDeUsuarios implements Serializable {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String matricula;
    private String gerencia;
    private String caminhoFoto;

    public CadastroDeUsuarios() {

    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase2.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child( getIdUsuario() );
        usuariosRef.setValue( this );
    }

    public void atualizar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase2.getFirebase();

        Map objeto = new HashMap();
        objeto.put("/usuarios/" + getIdUsuario() + "/nome", getNome() );
        objeto.put("/usuarios/" + getIdUsuario() + "/gerencia", getGerencia() );
        objeto.put("/usuarios/" + getIdUsuario() + "/matricula", getMatricula() );
        objeto.put("/usuarios/" + getIdUsuario() + "/caminhoFoto", getCaminhoFoto() );
        objeto.put("/usuarios/" + getIdUsuario() + "/email", getEmail() );

        firebaseRef.updateChildren( objeto );

    }




    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
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



