package com.example.corredor.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.corredor.R;
import com.google.android.gms.tasks.OnCompleteListener;

public class Detalhes_telefones_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView valia,bombeiros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_telefones_);
        inicializarComponetes();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.valiaid:

                Toast.makeText(getBaseContext(),"ok",Toast.LENGTH_LONG).show();

                break;

            case R.id.bombeirosid:

                Intent i =new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","0800",null));
                startActivity(i);

                break;

        }

    }


    public  void inicializarComponetes(){

        valia = findViewById(R.id.valiaid);
        bombeiros = findViewById(R.id.bombeirosid);

        valia.setOnClickListener(this);
        bombeiros.setOnClickListener(this);


    }

}
