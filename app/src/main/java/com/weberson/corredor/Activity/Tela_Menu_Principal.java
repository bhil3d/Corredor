package com.weberson.corredor.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.weberson.corredor.Adaptadores.Adapter_lista_Relatorios_Publicos;
import com.weberson.corredor.Class.CadastraRelatoriosTurno;
import com.weberson.corredor.Class.RecyclerItemClickListener;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.weberson.corredor.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Tela_Menu_Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth autenticacao;
    private Button butaomanutençao, butaoativo;
    FirebaseUser currentUser ;
    private GoogleSignInClient googleSignInClient;
    private RecyclerView recyclerView;
    private Adapter_lista_Relatorios_Publicos adapter_lista_relatorios_publicos;
    private List<CadastraRelatoriosTurno>listarelatorios = new ArrayList<>();
    private DatabaseReference relatoriosPublicosRef;
    private String identificadorUsuario;
    private ProgressBar progressBar;
    private String filtraManutençao = "";
    private String filtraAtivo = "";
    private  boolean filtroPorManutençao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_principalactivity_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurações iniciais
        autenticacao = FirebaseAuth.getInstance();
        currentUser = autenticacao.getCurrentUser();
        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        recyclerView = findViewById(R.id.recyclerRelatorios_Meusrelatorios);
        progressBar = findViewById(R.id.progressBar_Relatorios);
        //Configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        relatoriosPublicosRef = ConfiguracaoFirebase2.getFirebase()
                .child("relatorios");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();


        //Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter_lista_relatorios_publicos = new Adapter_lista_Relatorios_Publicos(listarelatorios, this);
        recyclerView.setAdapter( adapter_lista_relatorios_publicos );

        recuperaRelatoriosPublicos();

        //Aplicar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                CadastraRelatoriosTurno anuncioSelecionado = listarelatorios.get( position );
                                Intent i = new Intent(Tela_Menu_Principal.this, Tela_Detalhes_Relatorios_Publicos.class);
                                i.putExtra("anuncioSelecionado", anuncioSelecionado );
                                startActivity( i );
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }


    public void filtrarPorManutençao(View view){

        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("Selecione o tipo de manutençao");

        //Configurar spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Configura spinner de estados
        final Spinner spinnerEstado = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] estados = getResources().getStringArray(R.array.Manuteçao);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerEstado.setAdapter( adapter );

        dialogEstado.setView( viewSpinner );

        dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtraManutençao = spinnerEstado.getSelectedItem().toString();
                recuperarAnunciosPorEstado();
                filtroPorManutençao = true;
            }
        });

        dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = dialogEstado.create();
        dialog.show();

    }

    public void filtrarPorAtivo(View view){

        if( filtroPorManutençao == true ){

            AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
            dialogEstado.setTitle("Selecione o ativo desejada");

            //Configurar spinner
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

            //Configura spinner de categorias
            final Spinner spinnerCategoria = viewSpinner.findViewById(R.id.spinnerFiltro);
            String[] estados = getResources().getStringArray(R.array.Ativo);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item,
                    estados
            );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            spinnerCategoria.setAdapter( adapter );

            dialogEstado.setView( viewSpinner );

            dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filtraAtivo = spinnerCategoria.getSelectedItem().toString();
                    recuperarAnunciosPorCategoria();
                }
            });

            dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = dialogEstado.create();
            dialog.show();

        }else {
            Toast.makeText(this, "Escolha primeiro a manutençao!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void recuperarAnunciosPorCategoria(){

        progressBar.setVisibility(View.VISIBLE);

        //Configura nó por categoria
        relatoriosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorios")
                .child(filtraManutençao)
                .child( filtraAtivo );

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for(DataSnapshot anuncios: dataSnapshot.getChildren() ){

                    CadastraRelatoriosTurno anuncio = anuncios.getValue(CadastraRelatoriosTurno.class);
                    listarelatorios.add( anuncio );

                }

                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void recuperarAnunciosPorEstado(){

        progressBar.setVisibility(View.VISIBLE);

        //Configura nó por estado
        relatoriosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorios")
                .child(filtraManutençao);

        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listarelatorios.clear();
                for (DataSnapshot categorias: dataSnapshot.getChildren() ){
                    for(DataSnapshot anuncios: categorias.getChildren() ){

                        CadastraRelatoriosTurno anuncio = anuncios.getValue(CadastraRelatoriosTurno.class);
                        listarelatorios.add( anuncio );

                    }
                }

                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void recuperaRelatoriosPublicos() {


        listarelatorios.clear();
        relatoriosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                for(DataSnapshot manutecao: dataSnapshot.getChildren()){
                    for (DataSnapshot categorias: manutecao.getChildren() ){
                        for(DataSnapshot anuncios: categorias.getChildren() ){

                            CadastraRelatoriosTurno anuncio = anuncios.getValue(CadastraRelatoriosTurno.class);
                            listarelatorios.add( anuncio );



                        }
                    }
                }
                Collections.reverse( listarelatorios );
                adapter_lista_relatorios_publicos.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


        }

        if (id == R.id.editarPerfil) {

            Intent intent = new Intent(Tela_Menu_Principal.this,Editar_perfil_Activity.class);
            startActivity(intent);

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


        } else if (id == R.id.telefones) {



        } else if (id == R.id.contracheques) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ids-prd.valeglobal.net/nidp/idff/sso?id=MyWay&sid=1&option=credential&sid=1&target=https%3A%2F%2Fmyway.geo.valeglobal.net%2F")));

        } else if (id == R.id.passagensferroviarias) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://tremdepassageiros.vale.com/sgpweb/portal/index.html#/home")));

        } else if (id == R.id.idimagem_menu) {

            Intent intent = new Intent(Tela_Menu_Principal.this,Perfil_Usuario_Detalhes_Activity.class);
            startActivity(intent);
        }

        else if (id == R.id.poiticasP) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://corredor.web.app/politicas_de_privacidades.html")));
        }

        else if (id == R.id.Df) {

           /* Intent intent = new Intent(Tela_Menu_Principal.this, DfFragment.class);
            startActivity(intent);

            */
        }


        else if (id == R.id.termosUs) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://corredor.web.app/termos_de_servicos.html")));
        }
        else if (id == R.id.deslolgar) {

            /////////////////////////sair............Google..///////////////////////////////////////////////////
            FirebaseAuth.getInstance().signOut();

            LoginManager.getInstance().logOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(this, gso);
            googleSignInClient.signOut();

            /////////////sair///////////////////email///////////////////////////////////////
            autenticacao.signOut();
            startActivity(new Intent(getApplicationContext(), Tela_de_LougarActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void editarperfil (View view){

        Intent intent = new Intent(Tela_Menu_Principal.this,Perfil_Usuario_Detalhes_Activity.class);
        startActivity(intent);

    }

    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView navUserPhot = headerView.findViewById(R.id.idimagem_menu);
        TextView navUsername = headerView.findViewById(R.id.nomePerfilMp);
        TextView navUserMail = headerView.findViewById(R.id.emailPerfilMp);

        navUserMail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        //Recuperar foto do usuário
        Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Tela_Menu_Principal.this)
                    .load( url )
                    .into( navUserPhot );
        }

    }



}
