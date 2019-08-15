package com.studio.trabalho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ClienteAdapterList extends ArrayAdapter<Cliente> {

    private Context context;
    List<Cliente> lista;

    public ClienteAdapterList(Activity context, List<Cliente> lista ){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Cliente getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(Cliente item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cliente clientePossicao = this.lista.get(position);

        //estende o xml personalizado
        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_lista,null);

        //estende as variaveis do xml
        TextView txtNome = (TextView)convertView.findViewById(R.id.txtNome);
        TextView txtTelefone = (TextView)convertView.findViewById(R.id.txtTelefone);
        TextView txtEmail = (TextView)convertView.findViewById(R.id.txtEmail);
        ImageView imgFoto = (ImageView)convertView.findViewById(R.id.img);

        //atribue valores as variaveis
        txtNome.setText(clientePossicao.getNome());
        txtTelefone.setText(clientePossicao.getTelefone());
        txtEmail.setText(clientePossicao.getEmail());

        //Processo da foto
        byte[] fotoArray = clientePossicao.getImg();
        if (fotoArray != null){
            Bitmap raw = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            imgFoto.setImageBitmap(raw);
        }

        //retorna o xml personalizado
        return convertView;
    }
}
