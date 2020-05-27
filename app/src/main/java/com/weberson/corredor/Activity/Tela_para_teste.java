package com.weberson.corredor.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.weberson.corredor.Class.CadastroDeUsuarios;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.Class.Util3;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.weberson.corredor.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Tela_para_teste extends AppCompatActivity implements View.OnClickListener {


    private ImageView imageView;
    private ProgressBar progressBar;

    private EditText editText_Nome,editText_Matricula,editText_Gerencia;
    private EditText editText_Idade;

    private Button button_Alterar;
    private Button button_Remover;
    private TextView idfotoperfil;


    private CadastroDeUsuarios funcionarioSelecionado;
    private String identificadorUsuario;


    private Uri uri_imagem = null;
    private boolean imagem_Alterada = false;


    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_para_teste);



        //Configurações iniciais
        funcionarioSelecionado = UsuarioFirebase.getDadosUsuarioLogado();
        // storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();

        idfotoperfil = findViewById(R.id.caminhofoto);



        imageView = findViewById(R.id.imageView_Database_Dados_Funcionario);
        progressBar = findViewById(R.id.progressBar_Database_Dados_Funcionario);


        editText_Nome = findViewById(R.id.editText_Database_Dados_Funcionario_Nome);
        editText_Idade = findViewById(R.id.editText_Database_Dados_Funcionario_Idade);
        editText_Matricula = findViewById(R.id.editText_Database_Dados_Funcionario_Matricula);
        editText_Gerencia = findViewById(R.id.editText_Database_Dados_Funcionario_Gerencia);


        button_Alterar = findViewById(R.id.button_Database_Dados_Funconario_Alterar);
        button_Remover = findViewById(R.id.button_Database_Dados_Funconario_Remover);


        imageView.setOnClickListener(this);
        button_Alterar.setOnClickListener(this);
        button_Remover.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
       // progressBar.setVisibility(View.GONE);


        carregarDadosFuncionario();
        Recuperardadosdousuário();




    }

    private void carregarDadosFuncionario(){


        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        editText_Nome.setText(funcionarioSelecionado.getNome());
        editText_Idade.setText(funcionarioSelecionado.getEmail());
        editText_Gerencia.setText(funcionarioSelecionado.getGerencia());
        editText_Matricula.setText(funcionarioSelecionado.getMatricula());
        idfotoperfil.setText(funcionarioSelecionado.getCaminhoFoto());



        Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Tela_para_teste.this)
                    .load( url )
                    .into( imageView );
            progressBar.setVisibility(View.GONE);
        }
        else {
            imageView.setImageResource(R.drawable.avatar);
            progressBar.setVisibility(View.GONE);
        }



    }

    private void Recuperardadosdousuário(){

        //Configurações iniciais
        reference = ConfiguracaoFirebase2.getFirebase()
                .child("usuarios")
                .child(ConfiguracaoFirebase2 .getIdUsuario() );

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    CadastroDeUsuarios empresa = dataSnapshot.getValue(CadastroDeUsuarios.class);
                    editText_Nome.setText(empresa.getNome());
                    editText_Gerencia.setText(empresa.getCaminhoFoto());
                    editText_Matricula.setText(empresa.getMatricula());
                    editText_Idade.setText(empresa.getEmail());
                    idfotoperfil.setText(empresa.getCaminhoFoto());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageView_Database_Dados_Funcionario:


                obterImagem_Galeria();

                break;

            case R.id.button_Database_Dados_Funconario_Alterar:


                buttonAlterar();

                break;


            case R.id.button_Database_Dados_Funconario_Remover:

              //  buttonRemover();

                break;

        }

    }


    private void buttonAlterar(){



        String nome = editText_Nome.getText().toString();
        String idade = editText_Idade.getText().toString();
        String matricula = editText_Nome.getText().toString();
        String gerencia = editText_Idade.getText().toString();


        if(Util3.verificarCampos(getBaseContext(),nome,idade,matricula,gerencia)){




            if(!nome.equals(funcionarioSelecionado.getNome()) || idade != funcionarioSelecionado.getEmail() || matricula != funcionarioSelecionado.getMatricula() || gerencia != funcionarioSelecionado.getGerencia() || imagem_Alterada ){



                if( imagem_Alterada){

                    removerImagem(nome,idade,matricula,gerencia);


                }else{

                    alterarDados(nome,idade,gerencia,matricula,funcionarioSelecionado.getCaminhoFoto());
                }


            }else{

             //   DialogAlerta alerta = new DialogAlerta("Erro","Nenhuma informação foi alterada para poder salvar no Banco de Dados");
            //    alerta.show(getSupportFragmentManager(),"2");

            }


        }
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

                            imagem_Alterada = true;

                            return false;
                        }
                    }).into(imageView);

                }else{

                    Toast.makeText(getBaseContext(),"Falha ao selecionar imagem",Toast.LENGTH_LONG).show();

                }
            }

        }
    }



    private void removerImagem(final String nome, final String idade, final String matricula, final String gerencia){


      //  final DialogProgress progress = new DialogProgress();
       // progress.show(getSupportFragmentManager(),"1");

        String url = funcionarioSelecionado.getCaminhoFoto();

        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);



        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){


                 //   progress.dismiss();
                    salvarDadoStorage(nome, idade,gerencia,matricula);

                }else{


                 //   progress.dismiss();
                    Toast.makeText(getBaseContext(),"Erro ao Remover Imagem",Toast.LENGTH_LONG).show();

                }

            }
        });



    }




    private void salvarDadoStorage(final String nome, final String idade,final String matricula, final String gerencia){




        StorageReference reference = storage.getReference().child("BD").child("empresas").child(funcionarioSelecionado.getCaminhoFoto());

        final StorageReference nome_imagem = reference.child("CursoFirebase"+System.currentTimeMillis()+".jpg");



        Glide.with(getBaseContext()).asBitmap().load(uri_imagem).apply(new RequestOptions().override(1024,768))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                        Toast.makeText(getBaseContext(),"Erro ao transformar imagem",Toast.LENGTH_LONG).show();

                       // progress.dismiss();
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


                                   // progress.dismiss();
                                    Uri uri = task.getResult();

                                    String url_imagem = uri.toString();

                                    alterarDados(nome, idade,gerencia,matricula,url_imagem);

                                }else{

                                  //  progress.dismiss();
                                    Toast.makeText(getBaseContext(),"Erro ao realizar Upload - Storage",Toast.LENGTH_LONG).show();
                                }

                            }
                        });



                        return false;
                    }
                }).submit();

    }

    private void alterarDados(String nome, String idade, String url_imagem,String matricula,String gerencia){


       // final DialogProgress progress = new DialogProgress();
       // progress.show(getSupportFragmentManager(),"1");


        DatabaseReference reference = database.getReference().child("imagens").child("perfil").child(identificadorUsuario + ".jpeg");



        CadastroDeUsuarios funcionario = new CadastroDeUsuarios(nome,idade,matricula,gerencia,url_imagem);


        Map<String, Object> atualizacao = new HashMap<>();


        atualizacao.put(funcionarioSelecionado.getIdUsuario(),funcionario);


        reference.updateChildren(atualizacao).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){


                   // progress.dismiss();
                    Toast.makeText(getBaseContext(),"Sucesso ao Alterar Dados",Toast.LENGTH_LONG).show();
                    finish();

                }else{

                   // progress.dismiss();
                    Toast.makeText(getBaseContext(),"Erro ao Alterar Dados",Toast.LENGTH_LONG).show();

                }


            }
        });


    }


}
