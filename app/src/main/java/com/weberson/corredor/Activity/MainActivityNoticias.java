package com.weberson.corredor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.weberson.corredor.Class.CadastroDeUsuarios;
import com.weberson.corredor.Class.CadastroNoticias;
import com.weberson.corredor.Class.DialogAlerta;
import com.weberson.corredor.Class.DialogProgress;
import com.weberson.corredor.Class.Funcionario;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.Class.Util3;
import com.weberson.corredor.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivityNoticias extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout linearLayout;

    private EditText editText_Nome;
    private EditText editText_Idade;
    private CadastroDeUsuarios usuarioLogado;

    private Button button_Salvar;
    private ImageView imageView_Galeria;

    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private boolean imagem_Alterada = false;

    private Uri uri_imagem = null;

    private DialogProgress progress;
    private boolean imagem_Selecionada = false;

    private String identificadorUsuario;
    private CadastroDeUsuarios cadastroDeUsuarios;
    private Funcionario funcionarioSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_noticias);

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout_Database_Funcionario);

        editText_Nome = (EditText)findViewById(R.id.editText_Database_Funcionario_Nome);
        editText_Idade = (EditText)findViewById(R.id.editText_Database_Funcionario_Idade);

        button_Salvar = (Button)findViewById(R.id.button_Database_Funcionario_Salvar);
        imageView_Galeria = (ImageView)findViewById(R.id.imageView_Database_Funcionario_Imagem);

        button_Salvar.setOnClickListener(this);
        imageView_Galeria.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.button_Database_Funcionario_Salvar:


                buttonSalvar();

                break;

            case R.id.imageView_Database_Funcionario_Imagem:


                obterImagem_Galeria();

                break;

            case R.id.button_Database_Dados_Funconario_Alterar:



                break;

        }

    }

    private void buttonSalvar(){



        String nome = editText_Nome.getText().toString();

        String email = editText_Idade.getText().toString();




        if(Util3.verificarCampos(getBaseContext(),nome,email)){



            if(imagem_Selecionada){

                salvarDadosStorage(nome,email);

            }else{

                DialogAlerta alerta = new DialogAlerta("Imagem - Erro","É obrigatorio escolher uma imagem para Salvar os dados do Funcionário");
                alerta.show(getSupportFragmentManager(),"1");
            }

        }


    }

    //-----------------------------------------------MENU-----------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_database_lista_funcionario,menu);

        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()){


            case R.id.item_esconder_layout:


                linearLayout.setVisibility(View.GONE);

                return true;

            case R.id.item_mostrar_layout:

                linearLayout.setVisibility(View.VISIBLE);

                return true;


            case R.id.button_Database_Dados_Funconario_Alterar:


             //   buttonAlterar();

                break;

        }


        return super.onOptionsItemSelected(item);
    }

    //---------------------------------------- OBTER IMAGENS ----------------------------------------------------------------


    private void obterImagem_Galeria(){


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"),0);

    }

    //---------------------------------------- REPOSTAS DE COMUNICACAO ----------------------------------------------------------------




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK){


            if(requestCode == 0){ // RESPOSTA DA GALERIA

                if (data != null){ // CONTEUDO DA ESCOLHA DA IMAGEM DA GALERIA

                    uri_imagem = data.getData();

                    Glide.with(getBaseContext()).asBitmap().load(uri_imagem).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                            Toast.makeText(getBaseContext(),"Erro ao carregar imagem",Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                            imagem_Selecionada = true;

                            return false;
                        }
                    }).into(imageView_Galeria);

                }else{

                    Toast.makeText(getBaseContext(),"Falha ao selecionar imagem",Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    //----------------------------------------LIMPAR CAMPOS----------------------------------------------------------------





    //---------------------------------------- SALVAR DADOS----------------------------------------------------------------


    private void salvarDadosStorage(final String nome, final String email ){


        //Recuperar dados da imagem para o firebase
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        progress = new DialogProgress();
        progress.show(getSupportFragmentManager(),"2");



        StorageReference reference = storage.getReference().child("noticias").child(identificadorUsuario).child("imagens");

        final StorageReference nome_imagem = reference.child("imagemNoticias"+System.currentTimeMillis()+".jpg");

        Glide.with(getBaseContext()).asBitmap().load(uri_imagem).apply(new RequestOptions().override(1024,1000))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                        Toast.makeText(getBaseContext(),"Erro ao transformar imagem",Toast.LENGTH_LONG).show();

                        progress.dismiss();
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {



                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                        resource.compress(Bitmap.CompressFormat.JPEG,70, bytes);

                        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toByteArray());


                        try {
                            bytes.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        UploadTask uploadTask = nome_imagem.putStream(inputStream);



                        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){

                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {


                                return nome_imagem.getDownloadUrl();


                            }


                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {


                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {


                                if(task.isSuccessful()){


                                    progress.dismiss();

                                    Uri uri = task.getResult();

                                    String url_imagem = uri.toString();

                                    salvarDadosDatabase(nome, email, url_imagem);


                                }else{

                                    progress.dismiss();

                                    Toast.makeText(getBaseContext(),"Erro ao realizar Upload - Storage",Toast.LENGTH_LONG).show();
                                }

                            }
                        });



                        return false;
                    }
                }).submit();

    }



    private void salvarDadosDatabase(String titulo, String conteudo, String urlmagem){



        progress = new DialogProgress();
        progress.show(getSupportFragmentManager(),"2");


        CadastroNoticias funcionario = new CadastroNoticias(titulo,conteudo,urlmagem);


        DatabaseReference databaseReference = database.getReference().child("noticias").child(identificadorUsuario);



        databaseReference.push().setValue(funcionario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){


                    Toast.makeText(getBaseContext(),"Sucesso ao realizar Upload - Database",Toast.LENGTH_LONG).show();


                    progress.dismiss();

                }


                else{

                    Toast.makeText(getBaseContext(),"Erro ao realizar Upload - Database",Toast.LENGTH_LONG).show();
                    progress.dismiss();


                }

            }
        });
    }


}
