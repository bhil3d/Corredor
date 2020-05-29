package com.weberson.corredor.Class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Util3 {




    public static boolean statusInternet(Context context){

        ConnectivityManager conexao = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo informacao = conexao.getActiveNetworkInfo();

        if( informacao!=null && informacao.isConnected()){

            return true;

        }else{

            return false;
        }
    }



    public static boolean verificarCampos(Context context, String texto_1, String texto_2){


        if( !texto_1.isEmpty() && !texto_2.isEmpty() ){

            if( statusInternet(context)){

                return true;

            }else{

                Toast.makeText(context,"Sem conexão com a Internet", Toast.LENGTH_LONG).show();

                return false;
            }

        }else{

            Toast.makeText(context,"Preencha os campos", Toast.LENGTH_LONG).show();
            return false;
        }




    }

    public static boolean verificarCampos1(Context context, String texto_1, String texto_2, String texto_3, String texto_4){


        if( !texto_1.isEmpty() && !texto_2.isEmpty()&& !texto_3.isEmpty()&& !texto_4.isEmpty()){

            if( statusInternet(context)){

                return true;

            }else{

                Toast.makeText(context,"Sem conexão com a Internet", Toast.LENGTH_LONG).show();

                return false;
            }

        }else{

            Toast.makeText(context,"Preencha os campos", Toast.LENGTH_LONG).show();
            return false;
        }




    }




}
