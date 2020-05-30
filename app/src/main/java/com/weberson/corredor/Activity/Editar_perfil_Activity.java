package com.weberson.corredor.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.weberson.corredor.Class.CadastroDeUsuarios;
import com.weberson.corredor.Class.DialogAlerta;
import com.weberson.corredor.Class.DialogProgress;
import com.weberson.corredor.Class.Funcionario;
import com.weberson.corredor.Class.UsuarioFirebase;
import com.weberson.corredor.Class.Util3;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.weberson.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.weberson.corredor.Configuraçoes.Permissao;
import com.weberson.corredor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.weberson.corredor.Class.UsuarioFirebase.atualizarFotoUsuario;

public class Editar_perfil_Activity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView imageEditarPerfil;
    private TextInputEditText editNomePerfil, editEmailPerfil,editgerenciaPerfil,matriculalPerfil;
    private Button buttonSalvarAlteracoes;

    private CadastroDeUsuarios usuarioLogado;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageRef;
    private String identificadorUsuario;
    private DatabaseReference reference;
    private ProgressBar progrebar;
    private DialogProgress progress;

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private Uri uri_imagem = null;
    private boolean imagem_Selecionada = false;


    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1 );

        //Configurações iniciais
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();

      //  progrebar.setVisibility(View.GONE);

        //inicializar componentes
        inicializarComponentes();
        Recuperardadosdousuário();
        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        editNomePerfil.setText( usuarioPerfil.getDisplayName().toUpperCase() );
        editEmailPerfil.setText( usuarioPerfil.getEmail() );


       Uri url = usuarioPerfil.getPhotoUrl();
        if( url != null ){
            Glide.with(Editar_perfil_Activity.this)
                    .load( url )
                    .into( imageEditarPerfil );
        }
        else {
           imageEditarPerfil.setImageResource(R.drawable.avatar);
        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonSalvarAlteracoes:


                buttonSalvar();

                break;

            case R.id.imageEditarPerfil:


                obterImagem_Galeria();

                break;


        }


    }


    //---------------------------------------- OBTER IMAGENS ----------------------------------------------------------------


    private void obterImagem_Galeria(){


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"),0);

    }
//....................................final do codigo...............................................
private void buttonSalvar(){



    String nome = editNomePerfil.getText().toString();

    String email = editEmailPerfil.getText().toString();

    String gerencia = editgerenciaPerfil.getText().toString();

    String matricula =  matriculalPerfil.getText().toString();




    if(Util3.verificarCampos1(getBaseContext(),nome,email,gerencia,matricula)){



        if(imagem_Selecionada){

            salvarDadosStorage(nome,email,gerencia,matricula);

        }else{

            DialogAlerta alerta = new DialogAlerta("Imagem - Erro","É obrigatorio escolher uma imagem para Salvar os dados do Funcionário");
            alerta.show(getSupportFragmentManager(),"1");
        }

    }


}







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
                    }).into(imageEditarPerfil);

                }else{

                    Toast.makeText(getBaseContext(),"Falha ao selecionar imagem",Toast.LENGTH_LONG).show();

                }
            }

        }
    }
//....................................FIM............................................................
private void salvarDadosStorage(final String nome, final String email,final String gerencia, final String matricula  ){


    //Recuperar dados da imagem para o firebase
    ByteArrayOutputStream baos = new ByteArrayOutputStream();


    progress = new DialogProgress();
    progress.show(getSupportFragmentManager(),"2");



    StorageReference reference = storage.getReference().child("imagens").child("perfil");

    final StorageReference nome_imagem = reference.child(identificadorUsuario+".jpg");

    Glide.with(getBaseContext()).asBitmap().load(uri_imagem).apply(new RequestOptions().override(1024,768))
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

                                salvarDadosDatabase(nome, email,gerencia,matricula, url_imagem);
                                atualizarFotoUsuario( uri );

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



    private void salvarDadosDatabase(String nome, String email,String gerencia,String matricula, String caminhoFoto){



        progress = new DialogProgress();
        progress.show(getSupportFragmentManager(),"2");


        CadastroDeUsuarios funcionario = new CadastroDeUsuarios(nome,email,gerencia,matricula,caminhoFoto);


        DatabaseReference databaseReference = database.getReference().child("usuarios").child(identificadorUsuario);



        databaseReference.setValue(funcionario).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    editNomePerfil.setText(empresa.getNome());
                    editgerenciaPerfil.setText(empresa.getGerencia());
                    matriculalPerfil.setText(empresa.getMatricula());
                    editEmailPerfil.setText(empresa.getEmail());






                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void inicializarComponentes(){

        imageEditarPerfil      = findViewById(R.id.imageEditarPerfil);
        editNomePerfil         = findViewById(R.id.editNomePerfil);
        editEmailPerfil        = findViewById(R.id.editEmailPerfil);
        editgerenciaPerfil         = findViewById(R.id.editTextoGerencia);
        matriculalPerfil        = findViewById(R.id.editTextoMatricula);
       progrebar            = findViewById(R.id.progressBar_edite_perfil);
        buttonSalvarAlteracoes = findViewById(R.id.buttonSalvarAlteracoes);

        buttonSalvarAlteracoes.setOnClickListener(this);
        imageEditarPerfil.setOnClickListener(this);
        editEmailPerfil.setFocusable(false);

    }


}
