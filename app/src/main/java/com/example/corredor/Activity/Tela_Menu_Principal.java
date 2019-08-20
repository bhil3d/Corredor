package com.example.corredor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.corredor.Adaptadores.adpitadorFraguimentos;
import com.example.corredor.Configuraçoes.ConfiguraçaosFirebase;
import com.example.corredor.R;
import com.google.firebase.auth.FirebaseAuth;

public class Tela_Menu_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_principalactivity_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autenticacao = ConfiguraçaosFirebase.getFirebaseAutenticacao();

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        adpitadorFraguimentos Tbb =new adpitadorFraguimentos(getSupportFragmentManager());

        viewPager.setAdapter(Tbb);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            autenticacao.signOut();
            startActivity(new Intent(getApplicationContext(), Tela_de_LougarActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.relatorios) {

            Intent intent = new Intent(Tela_Menu_Principal.this, Tela_Lista_dos_Relatorio_Publicos.class);
            startActivity(intent);

        } else if (id == R.id.meus_relatorios) {

            Intent intent = new Intent(Tela_Menu_Principal.this, Tela_lista_meus_Relatorios.class);
            startActivity(intent);

        } else if (id == R.id.statusequipamentos) {
            Intent intent = new Intent(Tela_Menu_Principal.this, StatusEquipamentosActivity.class);
            startActivity(intent);

        } else if (id == R.id.telefones) {

        } else if (id == R.id.dss) {

        } else if (id == R.id.passagensferroviarias) {

        } else if (id == R.id.Df) {
            Intent intent = new Intent(Tela_Menu_Principal.this, AtualizardadosDF.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
