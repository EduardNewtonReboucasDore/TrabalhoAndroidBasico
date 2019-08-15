package com.studio.trabalho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class FormularioCliente extends Activity{

    EditText editNome, editTelefone, editEmail;
    ImageView editImg;
    Button btnSalvar, btnExcluir;

    BancoDados db = new BancoDados(this);

    private final int TIRAR_FOTO = 1;
    private Bitmap imageBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_cliente);

        iniciarCampos();

        final String codigo = getIntent().getExtras().getString("codigo");

        if(!codigo.isEmpty()){

            Cliente cliente = db.selecionaCliente(Integer.parseInt(codigo));

            editNome.setText(cliente.getNome());
            editTelefone.setText(cliente.getTelefone());
            editEmail.setText(cliente.getEmail());

            byte[] fotoArray = cliente.getImg();

            if (fotoArray != null){
                Bitmap raw = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
                editImg.setImageBitmap(raw);
            }
        }

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(takePictureIntent, TIRAR_FOTO);
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = editNome.getText().toString();
                String telefone = editTelefone.getText().toString();
                String email = editEmail.getText().toString();

                //processo de img
                Bitmap bitmap = ((BitmapDrawable) editImg.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                //---------------

                if (codigo.isEmpty()){

                    db.addCliente(new Cliente(nome, telefone, email, imageInByte));

                    atualizaPagina("Cliente adicionado!");

                }else{

                    Cliente cliente = db.selecionaCliente(Integer.parseInt(codigo));

                    cliente.setNome(nome);
                    cliente.setTelefone(telefone);
                    cliente.setEmail(email);
                    cliente.setImg(imageInByte);

                    db.atualizaCliente(cliente);

                    atualizaPagina("Cliente editado!");
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cliente cliente = db.selecionaCliente(Integer.parseInt(codigo));
                db.removerCliente(cliente);

                atualizaPagina("Cliente removido!");
            }
        });
    }

    public void iniciarCampos(){

        editNome = (EditText)findViewById(R.id.editNome);
        editTelefone = (EditText)findViewById(R.id.editTelefone);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editImg = (ImageView)findViewById(R.id.editFoto);
        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnExcluir = (Button)findViewById(R.id.btnExcluir);
    }

    public void atualizaPagina(String msg){

        Toast.makeText(FormularioCliente.this,"" + msg, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(FormularioCliente.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TIRAR_FOTO && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitMap = (Bitmap)extras.get("data");
            editImg.setImageBitmap(imageBitMap);
        }
    }
}
