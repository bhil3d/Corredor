package com.weberson.corredor.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.weberson.corredor.R;


public class Tela_para_teste extends AppCompatActivity implements View.OnClickListener {

private Button Start, Stop,Sumir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_para_teste);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


        }

    }




}
