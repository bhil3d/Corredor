package com.weberson.corredor.Class;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by jamiltondamasceno
 */

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){

        FirebaseAuth usuario = ConfiguracaoFirebase2.getFirebaseAutenticacao();
        return usuario.getCurrentUser();

    }

    public static String getIdentificadorUsuario(){
        return getUsuarioAtual().getUid();
    }

    public static void atualizarNomeUsuario(String nome){

        try {

            //Usuario logado no App
            FirebaseUser usuarioLogado = getUsuarioAtual();

            //Configurar objeto para alteração do perfil
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName( nome )

                    .build();
            usuarioLogado.updateProfile( profile ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if( !task.isSuccessful() ){
                        Log.d("Perfil","Erro ao atualizar nome de perfil." );
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void atualizarFotoUsuario(Uri url){

        try {

            //Usuario logado no App
            FirebaseUser usuarioLogado = getUsuarioAtual();

            //Configurar objeto para alteração do perfil
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setPhotoUri( url )
                    .build();
            usuarioLogado.updateProfile( profile ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if( !task.isSuccessful() ){
                        Log.d("Perfil","Erro ao atualizar a foto de perfil." );
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static CadastroDeUsuarios getDadosUsuarioLogado(){

        FirebaseUser firebaseUser = getUsuarioAtual();

        CadastroDeUsuarios usuario = new CadastroDeUsuarios();
        usuario.setEmail( firebaseUser.getEmail() );
        usuario.setNome( firebaseUser.getDisplayName() );
        usuario.setIdUsuario( firebaseUser.getUid() );

        if ( firebaseUser.getPhotoUrl() == null ){
            usuario.setCaminhoFoto("");
        }else{
            usuario.setCaminhoFoto( firebaseUser.getPhotoUrl().toString() );
        }

        return usuario;

    }


    public static void redirecionaUsuarioLogado(final Activity activity){

        FirebaseUser user = getUsuarioAtual();
        if(user != null ){
            Log.d("resultado", "onDataChange: " + getIdentificadorUsuario());
            DatabaseReference usuariosRef = ConfiguracaoFirebase.getFirebase()
                    .child("usuarios")
                    .child( getIdentificadorUsuario() );
            usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("resultado", "onDataChange: " + dataSnapshot.toString() );
                   // CadastroDeUsuarios usuario = dataSnapshot.getValue( CadastroDeUsuarios.class );


/*
                    String tipoUsuario = usuario.getTipoUsuario();
                    if( tipoUsuario.equals("M") ){
                        Intent i = new Intent(activity, Tela_Menu_Principal.class);
                        activity.startActivity(i);
                    }else {
                        Intent i = new Intent(activity, Tela_Lista_dos_Relatorio_Publicos.class);
                        activity.startActivity(i);
                    }


 */
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
