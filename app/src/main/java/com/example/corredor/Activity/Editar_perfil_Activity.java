package com.example.corredor.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.example.corredor.Class.CadastroDeUsuarios;
import com.example.corredor.Class.UsuarioFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase;
import com.example.corredor.Configuraçoes.ConfiguracaoFirebase2;
import com.example.corredor.Configuraçoes.Permissao;
import com.example.corredor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Editar_perfil_Activity extends AppCompatActivity {

    private CircleImageView imageEditarPerfil;
    private ImageView textAlterarFoto;
    private TextInputEditText editNomePerfil, editEmailPerfil,editgerenciaPerfil,matriculalPerfil;
    private Button buttonSalvarAlteracoes;
    private TextView idfotoperfil;
    private CadastroDeUsuarios usuarioLogado;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageRef;
    private String identificadorUsuario;
    private DatabaseReference reference;
    private ProgressBar progrebar;


    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_);

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1 );

        //Configurações iniciais
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



        //Salvar alterações do nome
        buttonSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeAtualizado = editNomePerfil.getText().toString();

                String emailAtualizado = editEmailPerfil.getText().toString();

                String matriculaAtualizado = matriculalPerfil.getText().toString();

                String gerenciaAtualizado = editgerenciaPerfil.getText().toString();

                String caminhoFoto        = idfotoperfil.getText().toString();


                //atualizar nome no perfil
                UsuarioFirebase.atualizarNomeUsuario( nomeAtualizado );

                //Atualizar nome no banco de dados
                usuarioLogado.setCaminhoFoto(caminhoFoto);
                usuarioLogado.setNome( nomeAtualizado );
                usuarioLogado.setEmail(emailAtualizado);
                usuarioLogado.setMatricula( matriculaAtualizado );
                usuarioLogado.setGerencia(gerenciaAtualizado);
                usuarioLogado.atualizar();

                Toast.makeText(Editar_perfil_Activity.this,
                        "Dados alterados com sucesso!",
                        Toast.LENGTH_SHORT).show();

            }
        });

        //Alterar foto do usuário
        textAlterarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progrebar.setVisibility(View.VISIBLE);
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA );
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
                    idfotoperfil.setText(empresa.getCaminhoFoto());





                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK ){
            Bitmap imagem = null;

            try {

                //Selecao apenas da galeria
                switch ( requestCode ){
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada );
                        break;
                }

                //Caso tenha sido escolhido uma imagem
                if ( imagem != null ){

                    //Configura imagem na tela
                    imageEditarPerfil.setImageBitmap( imagem );

                    //Recuperar dados da imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //Salvar imagem no firebase
                    StorageReference imagemRef = storageRef
                            .child("imagens")
                            .child("perfil")
                            .child( identificadorUsuario + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Editar_perfil_Activity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                         //  Recuperar local da foto
                            Uri url = taskSnapshot.getDownloadUrl();
                            atualizarFotoUsuario( url );


                            Toast.makeText(Editar_perfil_Activity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void atualizarFotoUsuario(Uri url){

        progrebar.setVisibility(View.VISIBLE);

        //Atualizar foto no perfil
        UsuarioFirebase.atualizarFotoUsuario( url );

        //Atualizar foto no Firebase
        usuarioLogado.setCaminhoFoto( url.toString() );
        usuarioLogado.atualizar();

        Toast.makeText(Editar_perfil_Activity.this,
                "Sua foto foi atualizada!",
                Toast.LENGTH_SHORT).show();

        progrebar.setVisibility(View.GONE);

    }

    public void inicializarComponentes(){

        imageEditarPerfil      = findViewById(R.id.imageEditarPerfil);
        textAlterarFoto        = findViewById(R.id.textAlterarFoto);
        editNomePerfil         = findViewById(R.id.editNomePerfil);
        editEmailPerfil        = findViewById(R.id.editEmailPerfil);
        editgerenciaPerfil         = findViewById(R.id.editTextoGerencia);
        matriculalPerfil        = findViewById(R.id.editTextoMatricula);
       idfotoperfil           = (TextView)findViewById(R.id.idfotoPF);
       progrebar            = findViewById(R.id.progressBar_edite_perfil);

        buttonSalvarAlteracoes = findViewById(R.id.buttonSalvarAlteracoes);
        editEmailPerfil.setFocusable(false);

    }
}
