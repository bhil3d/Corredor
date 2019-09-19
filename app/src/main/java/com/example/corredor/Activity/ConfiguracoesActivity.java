package com.example.corredor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.corredor.R;

public class ConfiguracoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        getSupportActionBar().setTitle("Configuraçoes");

    }
}
